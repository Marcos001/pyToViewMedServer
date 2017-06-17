import cv2 as c
import numpy as np

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





if __name__ == '__main__':
    kmeans_cv2(path_img='/home/mrv/IdeaProjects/pyToViewMedServer/src/com/lippo/marcos/data/extract/pim_imagem.png',
               nome_img='/home/mrv/IdeaProjects/pyToViewMedServer/src/com/lippo/marcos/data/extract/kmeans.png')