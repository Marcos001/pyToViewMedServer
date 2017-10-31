import unittest

import cv2

from com.lippo.marcos.PDI.outsu import segmentando_com_otsu

def fun(n):
    return n + 1

class MyTest(unittest.TestCase):

    def test(self):
        self.assertEqual(fun(3), 4)

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