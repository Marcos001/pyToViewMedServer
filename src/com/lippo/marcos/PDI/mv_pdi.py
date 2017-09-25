
import cv2

def ver_imagem():
    img = cv2.imread("/home/mrv/IdeaProjects/pyToViewMedServer/src/com/lippo/marcos/data/extract/pim_imagem.png")
    cv2.imshow("Imagem Enviada", img)
    cv2.waitKey(0)
    cv2.destroyAllWindows()


if __name__ == '__main__':
    ""
    ver_imagem()