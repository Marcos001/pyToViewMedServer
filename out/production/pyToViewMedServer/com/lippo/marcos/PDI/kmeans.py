import cv2 as c
import numpy as np
import os


def kmeans_cv2(path_img, nome_img):

    img = c.imread(path_img)

    Z = img.reshape((-1, 3))

    # converte para np.float32
    Z = np.float32(Z)

    # define criteria, numero de clusters(K) e aplica o kmeans()
    criteria = (c.TERM_CRITERIA_EPS + c.TERM_CRITERIA_MAX_ITER, 10, 1.0)
    K = 2
    ret, label, center = c.kmeans(Z, K, None, criteria, 10, c.KMEANS_RANDOM_CENTERS)

    # Agora converta de volta em uint8 e faça a imagem original
    center = np.uint8(center)
    res = center[label.flatten()]
    res2 = res.reshape((img.shape))

    c.imwrite(nome_img, res2)




def sobrepor_img_rgb(path_img, path_mask, path_new_img):
    ''

    img = c.imread(path_img)
    img_new = c.imread(path_mask)
    img_mask = c.imread(path_mask, 0)

    for i in range(img_mask.shape[0]):
        for j in range(img_mask.shape[1]):
            if img_mask[i][j] < 125:
                img_new[i][j][0] = img[i][j][0]
                img_new[i][j][1] = img[i][j][1]
                img_new[i][j][2] = img[i][j][2]

    print('salvando a imagem')
    c.imwrite(path_new_img, img_new)
    print('imagem ['+path_new_img+'] sobreposta e salva com sucesso!')



if __name__ == '__main__':

    path_img = os.getcwd()+'/src/com/lippo/marcos/data/extract/pim_imagem.png'
    path_saida = os.getcwd()+'/src/com/lippo/marcos/data/extract/segmentadas/kmeans.png'
    path_new_img = os.getcwd()+'/src/com/lippo/marcos/data/extract/sobrepostas/kmeans_sobreposta.png'

    import time

    ini = time.time()
    kmeans_cv2(path_img=path_img,
               nome_img=path_saida)

    #sobrepondo
    sobrepor_img_rgb(path_img=path_img, path_mask=path_saida, path_new_img=path_new_img)
    fim = time.time()

    file_ = open(os.getcwd()+'/src/com/lippo/marcos/data/arquivo/kmeans.txt', 'w')
    file_.write(str((fim-ini)))
    file_.close()

    print('done outsu')

