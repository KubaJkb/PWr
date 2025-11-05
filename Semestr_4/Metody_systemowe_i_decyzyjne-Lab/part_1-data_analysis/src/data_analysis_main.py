import argparse

from obesity_data_methods import *
from plots import *


def main():
        data = load_obesity_data()

        # stats = calculate_and_save_statistics(data)
        # num_stats, cat_stats = calculate_and_save_statistics_separate(data)

        hist_plot_hue(data)
        hist_plot(data)
        heat_map(data)
        violin_plot(data)
        reg_plot(data)
        box_plot(data)
        error_bars(data)


    #     parser = argparse.ArgumentParser(description='Analiza danych o otyłości')
    #     parser.add_argument('--input', type=str, default='data/ObesityDataSet.csv',
    #                         help='Ścieżka do pliku z danymi (domyślnie: data/ObesityDataSet.csv)')
    #     parser.add_argument('--plot', type=str, choices=[
    #         'box', 'violin', 'errorbars', 'hist', 'hist_hue', 'heatmap', 'regplot', 'all'
    #     ], help='Typ wykresu do wygenerowania')
    #     parser.add_argument('--stats', action='store_true',
    #                         help='Calculate and save basic statistics')
    #     parser.add_argument('--stats-separate', action='store_true',
    #                         help='Calculate and save statistics in separate files for numeric and categorical features')
    #
    #     args = parser.parse_args()
    #
    #     try:
    #         data = load_obesity_data(args.input)
    #
    #         if args.stats:
    #             output_file = "data_statistics"
    #             stats = calculate_and_save_statistics(data, output_file)
    #             print("\nStatystyki obliczone i zapisane pomyślnie")
    #             return
    #
    #         if args.stats_separate:
    #             output_prefix = "data_statistics"
    #             num_stats, cat_stats = calculate_and_save_statistics_separate(data, output_prefix)
    #             print("\nOddzielne statystyki obliczone i zapisane pomyślnie")
    #             return
    #
    #         if args.plot:
    #             plot_functions = {
    #                 'box': box_plot,
    #                 'violin': violin_plot,
    #                 'errorbars': error_bars,
    #                 'hist': hist_plot,
    #                 'hist_hue': hist_plot_hue,
    #                 'heatmap': heat_map,
    #                 'regplot': reg_plot
    #             }
    #
    #             if args.plot == 'all':
    #                 for func in plot_functions.values():
    #                     func(data)
    #             else:
    #                 plot_functions[args.plot](data)
    #
    #         print("Analiza zakończona pomyślnie")
    #
    #     except Exception as e:
    #         print(f"Błąd: {str(e)}")
    #
    # except Exception as e:
    #     print(f"Failed to load the data: {str(e)}")


if __name__ == "__main__":
    main()

