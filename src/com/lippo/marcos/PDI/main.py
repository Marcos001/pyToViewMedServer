import timeit
from com.lippo.marcos.PDI.outsu import binarizando_com_outsu

def calculando_tempo():

    tempo_otsu = timeit.timeit("binarizando_com_outsu({})".format("'/home/mrv/IdeaProjects/pyToViewMedServer/src/com/lippo/marcos/data/consumer.png'"), setup="from __main__ import binarizando_com_outsu", number=1)

    print('tempo levado para segmentação com otsu = '+str(tempo_otsu)+' segundos')


if __name__ == '__main__':
    print('running main...')
    calculando_tempo()
    print('done main.')