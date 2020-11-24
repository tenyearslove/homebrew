import os, sys

TORRENT_ROOT = ['/media/external/torrent', '/media/external/qbit/done']
SEMAPHORE = '/media/external/auto_torrent_upload.smp'

try:
    if not os.path.exists(SEMAPHORE):
        print('Create semaphore..')
        open(SEMAPHORE, 'w').close()

        for torrent in TORRENT_ROOT:
            path = os.walk(torrent)

            for root, directories, files in path:
                for file in files:
                    if not file.endswith(".part") and file != 'nohup.out' and not file.endswith(".nfo") and not file.endswith(".txt") and not file.endswith(".exe") and not file.startswith("."):
                        abs = os.path.join(root, file)
                        root_len = len(torrent)
                        subdir = root[root_len:]
                        command = 'rclone copy "' + abs + '" "PinkGirl-GD:/Upload/torrent' + subdir + '"'
                        print(command)
                        os.system(command)
except:
    print(sys.exec_info()[0])

print('Remove semaphore..')
os.remove(SEMAPHORE)