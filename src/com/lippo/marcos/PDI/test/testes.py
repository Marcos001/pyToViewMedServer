import unittest
import  numpy as np
import cv2, os

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


        self.assertTrue(isinstance(img, np.ndarray))             #
        self.assertTrue(isinstance(img_mask, np.ndarray))        #
        self.assertTrue(isinstance(img_sobreposta, np.ndarray))  #



    def test_verificar_imagem_valida_caso_Falso(self):

        img = cv2.imread('njbjvmvkcxnv live in havai')
        img_mask = cv2.imread('3 patinhos')
        img_sobreposta = cv2.imread('cafe hj, cafe amanha, e pinga')


        self.assertFalse(isinstance(img, np.ndarray))             #
        self.assertFalse(isinstance(img_mask, np.ndarray))        #
        self.assertFalse(isinstance(img_sobreposta, np.ndarray))  #


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

        def test_valida_parametros_arquivos_nao_existem(self):
            print('arquivo existe ?')

        path_img = 'sem arquivos'
        path_mask = 'manga'
        path_new_img = 'melancia'
        zip_file = 'ttt dxfdsf fdgfg'

        self.assertFalse(os.path.exists(path_img))
        self.assertFalse(os.path.exists(path_mask))
        self.assertFalse(os.path.exists(path_new_img))
        self.assertFalse(os.path.exists(zip_file))

    def test_valida_parametros_(self):
        print('arquivo existe ?')

        path_img = '/home/pavic/IdeaProjects/pyToViewMedServer/src/com/lippo/marcos/data/extract/pim_imagem.png'
        path_mask = '/home/pavic/IdeaProjects/pyToViewMedServer/src/com/lippo/marcos/data/extract/segmentadas/otsu.png'
        path_new_img = '/home/pavic/IdeaProjects/pyToViewMedServer/src/com/lippo/marcos/data/extract/sobrepostas/otsu_sobreposta.png'
        zip_file = '/home/pavic/IdeaProjects/pyToViewMedServer/src/com/lippo/marcos/data/extract/segmentadas/data_zip/send_zip.zip'

        self.assertTrue(os.path.exists(path_img))
        self.assertTrue(os.path.exists(path_mask))
        self.assertTrue(os.path.exists(path_new_img))
        self.assertTrue(os.path.exists(zip_file))


    def test_img_segmentada_caso_falso(self):
        '''verifica a quantidade de cluesters '''

        C_1 = -1
        C_2 = -1
        C_3 = -1

        path_img = '/home/pavic/IdeaProjects/pyToViewMedServer/src/com/lippo/marcos/connection/imagem.png'
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

        self.assertNotEqual(C_3,0)



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

    def test_sobrepor_img_rgb_valores_invalidos(self):

        path_img = '/home/pavic/IdeaProjects/pyToViewMedServer/src/com/lippo/marcos/data/extract/pim_imagem.png'
        path_mask = '/home/pavic/IdeaProjects/pyToViewMedServer/src/com/lippo/marcos/data/extract/segmentadas/otsu.png'

        img_RGB = cv2.imread(path_img, 0)
        img_mask = cv2.imread(path_mask, 0)

        # verificar se as dimensoes sao validas

        self.assertFalse((img_RGB.shape[0] == 0))
        self.assertFalse((img_RGB.shape[1] == 0))

        self.assertFalse((img_mask.shape[0] == 0))
        self.assertFalse((img_mask.shape[1] == 0))


    def test_sobrepor_img_rgb(self):

        path_img = '/home/pavic/IdeaProjects/pyToViewMedServer/src/com/lippo/marcos/data/extract/pim_imagem.png'
        path_mask = '/home/pavic/IdeaProjects/pyToViewMedServer/src/com/lippo/marcos/data/extract/segmentadas/otsu.png'

        img_RGB = cv2.imread(path_img, 0)
        img_mask = cv2.imread(path_mask, 0)

        # verificar se as dimensoes sao validas

        self.assertTrue((img_RGB.shape[0] > 0))
        self.assertTrue((img_RGB.shape[1] > 0))

        self.assertTrue((img_mask.shape[0] > 0))
        self.assertTrue((img_mask.shape[1] > 0))


    def test_vaalores_para_gerar_grafico(self):
        '''
        entrada
        processamento
        saida
        :return:
        '''
        print('test_vaalores_para_gerar_grafico()')

        from com.lippo.marcos.connection.gerar_grafico import  gerar_grafico


        print('entrada')
        path_file_time_otsu = '/home/pavic/IdeaProjects/pyToViewMedServer/src/com/lippo/marcos/data/arquivo/otsu.txt'
        path_file_time_kmeans = '/home/pavic/IdeaProjects/pyToViewMedServer/src/com/lippo/marcos/data/arquivo/kmeans.txt'

        self.assertTrue(os.path.exists(path_file_time_otsu))
        self.assertTrue(os.path.exists(path_file_time_kmeans))

        print('processamento')

        file_otsu = open(path_file_time_otsu, 'r')
        time_otsu = str(file_otsu.readline())
        file_otsu.close()

        file_ = open(path_file_time_kmeans, 'r')
        time_kmeans = str(file_.readline())
        file_.close()

        a = float(time_otsu)
        b = float(time_kmeans)

        self.assertIsNot(a, 0)
        self.assertIsNot(b, 0)

        #print('saida')
        #self.assertTrue(gerar_grafico(a, b))



if __name__ == '__main__':
    unittest.main()