import os, timeit, time
import matplotlib.pyplot as plt


def gerar_grafico(otsu, kmeans):
    ''''''
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
    plt.savefig("/home/mrv/IdeaProjects/pyToViewMedServer/src/com/lippo/marcos/data/grafico.png")
    print('figura salva!')
    #plt.show()


def get_valores_pro_grafico():
    ''''''

    file_ = open('/home/mrv/IdeaProjects/pyToViewMedServer/src/com/lippo/marcos/data/arquivo/otsu.txt', 'r')
    time_otsu = str(file_.readline())
    file_.close()

    file_ = open('/home/mrv/IdeaProjects/pyToViewMedServer/src/com/lippo/marcos/data/arquivo/kmeans.txt', 'r')
    time_kmeans = str(file_.readline())
    file_.close()


    a = float(time_otsu)
    b = float(time_kmeans)
    gerar_grafico(a, b)

if __name__ == '__main__':
    ''
    get_valores_pro_grafico()
