# Python 2 code, compatible with Python 3
import os
import shutil
import urllib.error
import urllib.request


def download(url, file_path, timeout=10):

    if os.path.exists(file_path):
        print("File \"" + file_path.split("/").pop() + "\" already existed!")
        if input('Do you want to delete and re-download it [Y/N]? ').lower() != 'y':
            print("The program is exiting according to your instruction. ")
            return
        else:
            os.remove(file_path)

    block_size = 1024 * 100  # 1kb
    tmp_file_path = file_path + ".part"
    file_size = -1

    try:
        redirect_count = 0
        while True:
            first_response = urllib.request.urlopen(url)
            if first_response.getcode() >= 400:
                raise Exception("Server has returned an error: %s" % first_response.getcode())
            elif first_response.getcode() >= 300:
                if redirect_count < 20:
                    url = first_response.geturl()
                    redirect_count += 1
                else:
                    raise Exception("Too many times of redirection. ")
            else:
                break

        # tmp file
        print("\nRemote URL: " + str(url))
        first_byte = os.path.getsize(tmp_file_path) if os.path.exists(tmp_file_path) else 0
        print("Downloading with urllib from Byte " + str(first_byte))

        file_size = int(first_response.info().get("Content-Length", -1))
        print("File size: " + str(file_size) + "\n")

        if first_response.info().get("Accept-Ranges", "none") == "none":
            try:
                print("Downloading... ", end="")
                headers = {
                    "Group": 1
                }
                req = urllib.request.Request(url, headers=headers)
                page = urllib.request.urlopen(req, timeout=timeout).read()
                print("OK")
                with open(tmp_file_path, "ab") as f:
                    f.write(page)
            except Exception as e:
                print("Caught Error: " + str(e))
                print("Retry...")
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
                    page = urllib.request.urlopen(req, timeout=timeout).read()
                    print("OK")

                    with open(tmp_file_path, "ab") as f:
                        f.write(page)
                    first_byte = last_byte + 1
                except Exception as e:
                    print("Caught Error: " + str(e))
                    print("Retry...")

    except urllib.error.HTTPError as e:
        print("Error! Code:", str(e.code))
    except urllib.error.URLError as e:
        print("Error! Reason:", e.reason)
    except Exception as e:
        print('Unexpected error!')
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
download("http://158.132.255.107:25003/project/team1.txt", "./team1.txt")
