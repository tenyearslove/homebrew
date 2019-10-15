from PIL import Image, ImageDraw, ImageFont
import os, errno

SEQUENCE = 22
BLANK_HIGHT = 150


def is_white_line(array, offset, white_line):
    return array[offset] >= white_line


def crop(image, page, start, end, dest_path, watermark):
    try:
        os.makedirs(dest_path, exist_ok=True)
    except OSError as exc:
        raise

    print((start, end))
    if start < end and image.size[1] and end < image.size[1]:
        crop = image.crop((0, start, image.size[0], end))

        width, height = crop.size

        draw = ImageDraw.Draw(crop)

        font = ImageFont.truetype('malgun.ttf', 20)
        text_width, text_height = draw.textsize(watermark, font)

        # calculate the x,y coordinates of the text
        margin = 5
        x = width - text_width - margin
        y = height - text_height - margin

        # draw watermark in the bottom right corner
        draw.text((x, y), watermark, font=font, fill=(0, 0, 0, 0))

        crop.save('{}/{}-{:02d}-{:04d}.jpg'.format(dest_path, SEQUENCE, page, start))


def process_page(image, name, page, dest_path):
    image_bw = image.convert('1')

    white_line = 255 * (image.size[0] - 5)

    a = [0] * image.size[1]
    for y in range(image.size[1]):
        for x in range(image.size[0]):
            pixel = image_bw.getpixel((x, y))
            a[y] += pixel

    list = []
    count = 0
    for cursor in range(image.size[1]):
        if is_white_line(a, cursor, white_line):
            count += 1
        else:
            if count > BLANK_HIGHT:
                list.append(cursor - int(count / 4))
            count = 0

    list.append(image.size[1] - 1)

    print("####")
    print(list)
    print("####")
    last = 0
    for pos in list:
        crop(image, page, last, pos, dest_path, name)
        last = pos

if __name__ == "__main__":
    for page in range(3, 4):
        im = Image.open('/home/siwon.sung/Phytoncide/KMC/22x/939BBD9B0580F254F0771851D899E99C_{}.jpg'.format(page))
        process_page(im, "", page, "/home/siwon.sung/Phytoncide/KMC")
