import unittest
import  numpy as np
import cv2

from com.lippo.marcos.PDI.outsu import segmentando_com_otsu

def fun(n):
    return n + 1

class MyTest(unittest.TestCase):

    def test(self):
        self.assertEqual(fun(3), 4)

    def test_verificar_imagem_valida(self):

        img = cv2.imread('/home/pavic/IdeaProjects/pyToViewMedServer/src/com/lippo/marcos/data/extract/pim_imagem.png')
        img_mask = cv2.imread('/home/pavic/IdeaProjects/pyToViewMedServer/src/com/lippo/marcos/data/extract/segmentadas/otsu.png')
        img_sobreposta = cv2.imread('/home/pavic/IdeaProjects/pyToViewMedServer/src/com/lippo/marcos/data/extract/sobrepostas/otsu_sobreposta.png')

        self.assertTrue(isinstance(img, np.ndarray))
        self.assertTrue(isinstance(img_mask, np.ndarray))
        self.assertTrue(isinstance(img_sobreposta, np.ndarray))


    def test_imagem_sobreposta(self):
        '''
        verifica se uma imagem foi sobreposta
        :return:
        '''

        path_img = '/home/pavic/IdeaProjects/pyToViewMedServer/src/com/lippo/marcos/data/extract/pim_imagem.png'
        path_sobreposta = '/home/pavic/IdeaProjects/pyToViewMedServer/src/com/lippo/marcos/data/extract/sobrepostas/otsu_sobreposta.png'

        img = cv2.imread(path_img)

        img_mask = cv2.imread(path_sobreposta, 0)
        img_sobreposta = cv2.imread(path_sobreposta)

        sobreposta = True

        for i in range(img_mask.shape[0]):
            for j in range(img_mask.shape[1]):
                if img_mask[i][j] != 0:
                    if img[i][j][0] != img_sobreposta[i][j][0]:
                        print(img[i][j] ,' = ', img_sobreposta[i][j])
                        sobreposta = False
                        break

        self.assertTrue(sobreposta)

    def test_img_segmentada(self):
        '''verifica a quantidade de cluesters '''

        C_1 = -1
        C_2 = -1
        C_3 = -1

        path_img = '/home/pavic/IdeaProjects/pyToViewMedServer/src/com/lippo/marcos/data/extract/segmentadas/otsu.png'
        imagem = segmentando_com_otsu(cv2.imread(path_img, 0))

        for i in range(imagem.shape[0]):
            for j in range(imagem.shape[1]):

                       if imagem[i][j] != C_1 and C_1 == -1:
                           C_1 = imagem[i][j]
                           print(' C1 =  ', C_1)

                       if C_1 != C_2 and C_2 == -1 and imagem[i][j] != C_1:
                           C_2 = imagem[i][j]
                           print(' C2 =  ', C_2)

                       if imagem[i][j] != C_1 and imagem[i][j] != C_2 and C_3 == -1:
                           C_3 = imagem[i][j]
                           print(' C3 =  ', C_3)

        self.assertEqual(C_3,-1)

if __name__ == '__main__':
    unittest.main()