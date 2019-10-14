from PIL import Image

im = Image.open('/home/siwon.sung/Phytoncide/KMC/22x/939BBD9B0580F254F0771851D899E99C_3.jpg')
im_bw = im.convert('1')

HIGHT = 10

whiteLine = 255 * (im.size[0]-1)
print(whiteLine)

def is_text(array, offset):
    count = 0
    for i in range(HIGHT):
        if not is_white_line(array, offset + i):
            count += 1

    return count == HIGHT


def is_blank(array, offset):
    count = 0
    for i in range(HIGHT):
        if is_white_line(array, offset + i):
            count += 1

    return count == HIGHT


def is_white_line(array, offset):
    return array[offset] >= whiteLine


a = [0] * im.size[1]

for y in range(im.size[1]):
    for x in range(im.size[0]):
        pixel = im_bw.getpixel((x, y))
        a[y] += pixel

for i in range(im.size[1]):
    print('{} : {}'.format(i, '빈구간' if a[i] >= whiteLine else a[i]))

cursor = 0
start = 0
end = 0
while cursor < im.size[1] - HIGHT:
    if not is_text(a, cursor):
        cursor += 1
        continue
    else:
        start = cursor

    if not is_blank(a, cursor):
        cursor += 1
        continue
    else:
        end = cursor

    if start > 0 and end > 0:
        break

print((start, end))
# crop = im.crop((0, start, im.size[0], end))
# crop.save('/home/siwon.sung/Phytoncide/KMC/22x/hoho.jpg')
