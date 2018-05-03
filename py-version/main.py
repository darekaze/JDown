# compatible with Python 3
import os
import shutil
import urllib.error
import urllib.request

size_unit = ("B", "KB", "MB", "GB", "TB")


class NoRedirection(urllib.request.HTTPErrorProcessor):
    def http_response(self, request, response):
        return response
    https_response = http_response


opener = urllib.request.build_opener(NoRedirection)
block_size = 1024 * 100  # 100KB


def percentage(a, b=100, round_digit=2):
    return str(round(a * 100 / b, round_digit)) + "%"


def check_url(url):
    redirect_count = 0
    while True:  # Stage 1: Remote file detection
        res = opener.open(urllib.request.Request(url, method="HEAD"))
        if res.getcode() >= 400:  # Error handling
            raise Exception("Server returned an error! Error code: " + str(res.getcode()))
        elif res.getcode() >= 300:  # Redirect handling
            if redirect_count < 20:
                url = res.geturl()
                redirect_count += 1
            else:
                raise Exception("Too many times of redirection. ")
        else:  # Success
            return res


def download(url="http://158.132.255.107:25003/project/team1.txt", file_path=None, timeout=10):

    file_size = -1
    tmp_file_path = None

    try:
        first_response = check_url(url)
        url = first_response.geturl()

        if file_path is None:   # Extract remote filename automatically
            remote_filename = url.split("/")[-1].split("?")[0]
            if remote_filename == "":
                remote_filename = "index"
            file_path = "./" + remote_filename

        if os.path.exists(file_path):
            print("File \"" + file_path.split("/")[-1] + "\" already existed!")
            if input('Do you want to delete and re-download it [Y/N]? ').lower() != 'y':
                print("The program is exiting according to your instruction. ")
                return
            else:
                os.remove(file_path)

        tmp_file_path = file_path + ".part"     # temp file

        print("\nRemote URL: " + str(url))

        # Generate human-readable file size
        file_size = int(first_response.info().get("Content-Length", -1))
        readable_size = file_size
        readable_size_unit = 0
        while readable_size >= 1000:
            readable_size /= 1024
            readable_size_unit += 1
        readable_size = str(round(readable_size, 2)) + " " + size_unit[readable_size_unit]
        print("File size: " + readable_size)

        is_resumable = first_response.info().get("Accept-Ranges", "none") != "none"
        if not is_resumable:
            print("Server does not allow resumable downloading. ")

        first_byte = os.path.getsize(tmp_file_path) if os.path.exists(tmp_file_path) else 0
        print("Downloading with urllib from " + percentage(first_byte, file_size) + "...\n")

        if not is_resumable:
            print("Downloading... ", end="")
            headers = {"Group": 1}
            req = urllib.request.Request(url, headers=headers)
            page = opener.open(req, timeout=timeout).read()
            print("OK")
            with open(tmp_file_path, "ab") as f:
                f.write(page)
        else:
            while first_byte < file_size:
                try:
                    if first_byte + block_size - 1 >= file_size:
                        last_byte = file_size
                    else:
                        last_byte = first_byte + block_size - 1

                    print("Downloading from Byte " + str(first_byte) + " to Byte " + str(last_byte) + '... ', end='')
                    headers = {
                        "Range": "bytes=%s-%s" % (first_byte, last_byte),
                        "Group": 1
                    }
                    req = urllib.request.Request(url, headers=headers)
                    page = opener.open(req, timeout=timeout).read()
                    print(percentage(last_byte, file_size))

                    with open(tmp_file_path, "ab") as f:
                        f.write(page)
                    first_byte = last_byte + 1
                except Exception as e:
                    print("Caught Error: " + str(e))
                    print("Retry...")

    except urllib.error.HTTPError as e:
        print("Server returned an error! Code:", str(e.code))
    except urllib.error.URLError as e:
        print("Error! Reason:", e.reason)
    except Exception as e:
        print("Error: ", end="")
        print(e)
    finally:
        # rename the temp download file to the correct name if fully downloaded
        if file_size == -1:
            pass
        elif file_size == os.path.getsize(tmp_file_path):
            shutil.move(tmp_file_path, file_path)
            print("\nCompleted. ")

    return


print("Resumable HTTP Downloader\nGroup 1, COMP2322, PolyU\n")
download()
