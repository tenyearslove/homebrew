import os, re, sys
from datetime import datetime, timedelta

SRC_ROOT = '/var/transmission/sjva_ktv/onair'
DST_ROOT = '/media/external/sjva/sjva_ktv'
SEMAPHORE = '/media/external/auto_copy_external.smp'

p = re.compile('\d{2}(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])')

try:
    a = os.path.exists(SEMAPHORE)

    if not os.path.exists(SEMAPHORE):
        print('Create semaphore..')
        open(SEMAPHORE, 'w').close()

        src_path = os.walk(SRC_ROOT)

        for root, directories, files in src_path:
            for file in files:
                m = p.search(file)
                if m is not None:
                    abs = '"' + os.path.join(root, file) + '"'
                    root_len = len(SRC_ROOT)
                    subdir = root[root_len:]
                    destdir = '"' + DST_ROOT + subdir + '"'
                    command = 'mkdir -p ' + destdir + '; cp -r ' + abs + ' ' + destdir + "; "
                    print(command)
                    os.system(command)

        dst_path = os.walk(DST_ROOT)

        for root, directories, files in dst_path:
            for file in files:
                abs = os.path.join(root, file)
                mtime = datetime.fromtimestamp(os.path.getmtime(abs))
                ptime = datetime.now() - timedelta(days=30)

                print(mtime)
                print(ptime)

                if mtime < ptime:
                    os.remove(abs)
except:
    print(sys.exec_info()[0])

print('Remove semaphore..')
os.remove(SEMAPHORE)
