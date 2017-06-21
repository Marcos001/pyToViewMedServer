
import cv2

def ver_imagem():
    img = cv2.imread("/home/mrv/IdeaProjects/pyToViewMedServer/src/com/lippo/marcos/data/extract/pim_imagem.png")
    cv2.imshow("Imagem Enviada", img)
    cv2.waitKey(0)
    cv2.destroyAllWindows()

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

    cv2.imwrite(path_new_img, img_new)
    print('imagem '+path_new_img+' sobreposta e sala com sucesso!')

if __name__ == '__main__':
    ""
    ver_imagem()