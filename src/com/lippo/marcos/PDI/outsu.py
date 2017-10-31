import cv2
import time

def binarizando_com_outsu(path_img, path_saida):
    '''
    Aqui vem na binarização de Otsu.
    Este algoritmo irá permitir-nos obter de forma
    rápida e automaticamente o valor limite
    correto para escolher entre dois modo de histograma,
    permitindo-lhes aplicar o limiar de forma otimizada'''

    img = cv2.imread(path_img, 0)

    ret, imgf = cv2.threshold(img, 0, 255, cv2.THRESH_BINARY + cv2.THRESH_OTSU)

    print('salvando imagem')
    cv2.imwrite(path_saida, imgf)
    print('Segmentada com Otsu')



def sobrepor_img_rgb(path_img, path_mask, path_new_img):
    ''
    img = cv2.imread(path_img)
    img_new = cv2.imread(path_mask)
    img_mask = cv2.imread(path_mask, 0)

    for i in range(img_mask.shape[0]):
        for j in range(img_mask.shape[1]):
            if img_mask[i][j] > 0:
                img_new[i][j][0] = img[i][j][0]
                img_new[i][j][1] = img[i][j][1]
                img_new[i][j][2] = img[i][j][2]

    print('salvando a imagem')
    cv2.imwrite(path_new_img, img_new)
    print('imagem ['+path_new_img+'] sobreposta e salva com sucesso!')




"""************************************************************************************************************"""


def ver_imagem(img):
    cv2.imshow("Imagem segmentada", img)
    cv2.waitKey(0)
    cv2.destroyAllWindows()


def segmentando_com_otsu(imagem):

    ret, img_segmentada = cv2.threshold(imagem, 0, 255, cv2.THRESH_BINARY + cv2.THRESH_OTSU)

    print('Segmentada com Otsu')
    return img_segmentada



def _sobrepor_img_rgb(img_RGB, img_mask):

    for i in range(img_mask.shape[0]):
        for j in range(img_mask.shape[1]):
            if img_mask[i][j] == 0:
                img_RGB[i][j][0] = 0
                img_RGB[i][j][1] = 0
                img_RGB[i][j][2] = 0

    return img_RGB


def main_otsu(imagem):

    gray_imagem = cv2.cvtColor(imagem, cv2.COLOR_BGR2GRAY)

    imagem_segmentada = segmentando_com_otsu(gray_imagem)

    imagem_sobreposta = _sobrepor_img_rgb(imagem, imagem_segmentada)

    #x = np.array(imagem_sobreposta)
    #x = imagem_sobreposta.tobytes()
    cv2.imwrite('imagem.png', imagem_sobreposta)

    f = open(file='imagem.png', mode='rb')
    i = f.read()
    print('tipo do img = ', type(imagem_sobreposta))
    print('tipo do arquivo = ', type(i))

    print('retornando...')

    return i


    # enviar ao cliente as 3 imageens


if __name__ == '__main__':
    ''

    path_img = '/home/pavic/IdeaProjects/pyToViewMedServer/src/com/lippo/marcos/data/extract/pim_imagem.png'
    path_saida = '/home/pavic/IdeaProjects/pyToViewMedServer/src/com/lippo/marcos/data/extract/segmentadas/otsu.png'
    path_new_img = '/home/pavic/IdeaProjects/pyToViewMedServer/src/com/lippo/marcos/data/extract/sobrepostas/otsu_sobreposta.png'

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

