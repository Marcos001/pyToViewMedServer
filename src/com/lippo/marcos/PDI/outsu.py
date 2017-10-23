import numpy as np
from matplotlib import pyplot as plt
import cv2
import time, os



def ver_imagem(img):
    cv2.imshow("Imagem segmentada", img)
    cv2.waitKey(0)
    cv2.destroyAllWindows()


def segmentando_com_otsu(imagem):

    ret, img_segmentada = cv2.threshold(imagem, 0, 255, cv2.THRESH_BINARY + cv2.THRESH_OTSU)

    print('Segmentada com Otsu')
    return img_segmentada



def sobrepor_img_rgb(img_RGB, img_mask):

    for i in range(img_mask.shape[0]):
        for j in range(img_mask.shape[1]):
            if img_mask[i][j] == 0:
                img_RGB[i][j][0] = 0
                img_RGB[i][j][1] = 0
                img_RGB[i][j][2] = 0

    return img_RGB

def main_otsu(imagem):

    ver_imagem(imagem)

    gray_imagem = cv2.cvtColor(imagem, cv2.COLOR_BGR2GRAY)

    imagem_segmentada = segmentando_com_otsu(gray_imagem)

    imagem_sobreposta = sobrepor_img_rgb(imagem, imagem_segmentada)

    ver_imagem(imagem_sobreposta)




