from ProcessPDF import process_pdf


KMC_PATH = "/home/siwon.sung/Phytoncide/KMC"
PDF_PATH = KMC_PATH + "/KMC_후기_초3_예선_2017_36회_문제.pdf"
OUTPUT_PATH = KMC_PATH + "/OUTPUT"

if __name__ == "__main__":
    process_pdf(PDF_PATH, OUTPUT_PATH)