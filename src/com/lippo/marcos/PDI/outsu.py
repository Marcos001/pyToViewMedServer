import numpy as np
from matplotlib import pyplot as plt
import cv2
import time

def vendo_histograma_imagem(path_img ):
    '''análise usando OpenCV para obter o histograma da imagem e veja se a imagem é bimodal'''

    img = cv2.imread(path_img, 0)
    plt.subplot(2, 1, 1), plt.imshow(img, cmap='gray')
    plt.title('Original Noisy Image '), plt.xticks([]), plt.yticks([])
    plt.subplot(2, 1, 2), plt.hist(img.ravel(), 256)
    plt.title('Histograma'), plt.xticks([]), plt.yticks([])
    plt.show()

def binarizando_com_limiar(path_img):
    ''' binarizando com cv2.threshold() '''

    img = cv2.imread(path_img, 0)

    # ret1, th1 = cv2.threshold(img, 127, 255, cv2.THRESH_BINARY)

    ret, imgf = cv2.threshold(img, 0, 255, cv2.THRESH_BINARY + cv2.THRESH_OTSU)

    # blur = cv2.GaussianBlur(img, (5,5), 0)
    # ret3, th3 = cv2.threshold(blur, 0, 255, cv2.THRESH_BINARY+cv2.THRESH_OTSU)

    plt.subplot(3, 1, 1), plt.imshow(img, cmap='gray')
    plt.title('Original Noisy Image'), plt.xticks([]), plt.yticks([])
    plt.subplot(3, 1, 2), plt.hist(img.ravel(), 256)
    plt.axvline(x=ret, color='r', linestyle='dashed', linewidth=2)
    plt.title('Histogram'), plt.xticks([]), plt.yticks([])
    plt.subplot(3, 1, 3), plt.imshow(imgf, cmap='gray')
    plt.title('Otsu thresholding'), plt.xticks([]), plt.yticks([])
    plt.show()

def binarizando_com_outsu(path_img, path_saida):
    '''
    Aqui vem na binarização de Otsu.
    Este algoritmo irá permitir-nos obter de forma
    rápida e automaticamente o valor limite
    correto para escolher entre dois modo de histograma,
    permitindo-lhes aplicar o limiar de forma otimizada'''

    img = cv2.imread(path_img, 0)

    ret, imgf = cv2.threshold(img, 0, 255, cv2.THRESH_BINARY + cv2.THRESH_OTSU)

    #plt.subplot(3, 1, 1), plt.imshow(img, cmap='gray')
    #plt.title('Img Original em tons de zinza'), plt.xticks([]), plt.yticks([])
    #plt.subplot(3, 1, 2), plt.hist(img.ravel(), 256)
    #plt.axvline(x=ret, color='r', linestyle='dashed', linewidth=2)
    #plt.title('Histogram'), plt.xticks([]), plt.yticks([])
    #plt.subplot(3, 1, 3), plt.imshow(imgf, cmap='gray')
    #plt.title('Otsu thresholding'), plt.xticks([]), plt.yticks([])
    #plt.show()
    print('salvando imagem')
    cv2.imwrite(path_saida, imgf)
    print('Segmentada com Otsu')


def get_valores_limiar(path_img):
    '''
    calculando limiar
    :param path_img:
    :return:
    '''

    img = cv2.imread(path_img, 0)
    # blur = cv2.GaussianBlur(img,(5,5),0)

    # find normalized_histogram, and its cumulative distribution functio
    hist = cv2.calcHist([img], [0], None, [256], [0, 256])
    hist_norm = hist.ravel() / hist.max()
    Q = hist_norm.cumsum()
    bins = np.arange(256)
    fn_min = np.inf
    thresh = -1
    for i in range(1, 256):
        p1, p2 = np.hsplit(hist_norm, [i])  # probabilities
        q1, q2 = Q[i], Q[255] - Q[i]  # cum sum of classes
        if q1 == 0:
            q1 = 0.00000001
        if q2 == 0:
            q2 = 0.00000001
        b1, b2 = np.hsplit(bins, [i])  # weights
        # finding means and variances
        m1, m2 = np.sum(p1 * b1) / q1, np.sum(p2 * b2) / q2
        v1, v2 = np.sum(((b1 - m1) ** 2) * p1) / q1, np.sum(((b2 - m2) ** 2) * p2) / q2
        # calculates the minimization function
        fn = v1 * q1 + v2 * q2
        #if fn & lt & :
        #   fn_min = fn
        thresh = i
    # find otsu's threshold value with OpenCV function
    ret, otsu = cv2.threshold(img, 0, 255, cv2.THRESH_BINARY + cv2.THRESH_OTSU)
    print( thresh, ' <> ', ret)

def sobrepor_img_rgb(path_img, path_mask, path_new_img):
    ''
    img = cv2.imread(path_img)
    img_new = cv2.imread(path_mask)
    img_mask = cv2.imread(path_mask, 0)

    for i in range(img_mask.shape[0]):
        for j in range(img_mask.shape[1]):
            if img_mask[i][j] < 125:
                img_new[i][j][0] = img[i][j][0]
                img_new[i][j][1] = img[i][j][1]
                img_new[i][j][2] = img[i][j][2]

    print('salvando a imagem')
    cv2.imwrite(path_new_img, img_new)
    print('imagem ['+path_new_img+'] sobreposta e salva com sucesso!')


if __name__ == '__main__':
    ''

    path_img = '/home/mrv/IdeaProjects/pyToViewMedServer/src/com/lippo/marcos/data/extract/pim_imagem.png'
    path_saida = '/home/mrv/IdeaProjects/pyToViewMedServer/src/com/lippo/marcos/data/extract/segmentadas/otsu.png'
    path_new_img = '/home/mrv/IdeaProjects/pyToViewMedServer/src/com/lippo/marcos/data/extract/sobrepostas/otsu_sobreposta.png'

    ini = time.time()
    binarizando_com_outsu(path_img=path_img, path_saida=path_saida)



    print('sobreponto > ')
    sobrepor_img_rgb(path_img=path_img, path_mask=path_saida, path_new_img=path_new_img)
    print('sobreposta jah')
    fim = time.time()

    file_ = open('/home/mrv/IdeaProjects/pyToViewMedServer/src/com/lippo/marcos/data/arquivo/otsu.txt', 'w')
    file_.write(str((fim-ini)))
    file_.close()

    print('done outsu')
