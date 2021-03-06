# PDF TO IMAGE CONVERSION
# IMPORT LIBRARIES
import pdf2image
from PIL import Image
import time
from ProcessImage import process_page

# DECLARE CONSTANTS
KMC_PATH = "/home/siwon.sung/Phytoncide/HME"
PDF_PATH = KMC_PATH + "/HME_2005_하_초1_문제.pdf"
OUTPUT_PATH = KMC_PATH + "/OUTPUT"

DPI = 200
OUTPUT_FOLDER = None
FIRST_PAGE = None
LAST_PAGE = None
FORMAT = 'jpg'
THREAD_COUNT = 1
USERPWD = None
USE_CROPBOX = False
STRICT = False


def pdftopil(pdf_path):
    # This method reads a pdf and converts it into a sequence of images
    # PDF_PATH sets the path to the PDF file
    # dpi parameter assists in adjusting the resolution of the image
    # output_folder parameter sets the path to the folder to which the PIL images can be stored (optional)
    # first_page parameter allows you to set a first page to be processed by pdftoppm
    # last_page parameter allows you to set a last page to be processed by pdftoppm
    # fmt parameter allows to set the format of pdftoppm conversion (PpmImageFile, TIFF)
    # thread_count parameter allows you to set how many thread will be used for conversion.
    # userpw parameter allows you to set a password to unlock the converted PDF
    # use_cropbox parameter allows you to use the crop box instead of the media box when converting
    # strict parameter allows you to catch pdftoppm syntax error with a custom type PDFSyntaxError

    start_time = time.time()
    pil_images = pdf2image.convert_from_path(pdf_path, dpi=DPI, output_folder=OUTPUT_FOLDER, first_page=FIRST_PAGE,
                                             last_page=LAST_PAGE, fmt=FORMAT, thread_count=THREAD_COUNT, userpw=USERPWD,
                                             use_cropbox=USE_CROPBOX, strict=STRICT)
    print("Time taken : " + str(time.time() - start_time))
    return pil_images


def save_images(pil_images, output_path, name):
    # This method helps in converting the images in PIL Image file format to the required image format
    index = 1
    for image in pil_images:
        process_page(image, name, index, output_path)
        # image.save("page_{:02d}.jpg".format(index))
        index += 1


def process_pdf(pdf_path, output_path, column=1):
    pil_images = pdftopil(pdf_path)
    target_image = pil_images if column == 1 else split_page(pil_images)
    save_images(target_image, output_path, get_file_name_without_ext(pdf_path))


def get_file_name_without_ext(path):
    return path.split("/")[-1].split(".")[0]


def split_page(images):
    splited = []
    for img in images:
        x, y = img.size
        center = int(x / 2)
        left = img.crop((0, 0, center-10, y))
        right = img.crop((center+10, 0, x, y))

        splited.append(left)
        splited.append(right)

    return splited


if __name__ == "__main__":
    print(PDF_PATH)
    process_pdf(PDF_PATH, OUTPUT_PATH)
