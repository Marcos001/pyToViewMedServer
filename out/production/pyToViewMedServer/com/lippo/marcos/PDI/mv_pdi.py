
import cv2, os


def ver_imagem():
    img = cv2.imread(os.getcwd()+'/src/com/lippo/marcos/data/extract/pim_imagem.png')
    cv2.imshow("Imagem Enviada", img)
    cv2.waitKey(0)
    cv2.destroyAllWindows()


if __name__ == '__main__':
    ""
    ver_imagem()