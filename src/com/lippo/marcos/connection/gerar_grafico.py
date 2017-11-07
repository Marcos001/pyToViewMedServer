import os
import matplotlib.pyplot as plt


def gerar_grafico(otsu, kmeans):
    ''''''
    try:
        y_axis = [otsu, kmeans]
        x_axis = range(len(y_axis))
        width_n = 0.4
        bar_color = 'green'
        algortirmos = ['Otsu', 'Kmeans']

        plt.bar(x_axis, y_axis, width=width_n, color=bar_color, align='center')
        plt.ylabel('Tempo')
        plt.xlabel('Algoritmos')
        plt.xticks(x_axis, algortirmos, rotation='vertical')
        plt.title('Tempo de execução!')
        plt.tight_layout()
        plt.grid(True)
        plt.savefig(os.getcwd()+'/src/com/lippo/marcos/data/grafico.png')
        print('figura salva!')
        print(True)
        return True
    except:
        print("false")
        return False


def get_valores_pro_grafico():
    ''''''

    path_file_otsu = os.getcwd()+'/src/com/lippo/marcos/data/arquivo/otsu.txt'
    path_file_kmeans = os.getcwd()+'/src/com/lippo/marcos/data/arquivo/kmeans.txt'

    file_otsu = open(path_file_otsu, 'r')
    time_otsu = str(file_otsu.readline())
    file_otsu.close()

    file_ = open(path_file_kmeans, 'r')
    time_kmeans = str(file_.readline())
    file_.close()


    a = float(time_otsu)
    b = float(time_kmeans)
    gerar_grafico(a, b)

if __name__ == '__main__':
    ''
    get_valores_pro_grafico()
