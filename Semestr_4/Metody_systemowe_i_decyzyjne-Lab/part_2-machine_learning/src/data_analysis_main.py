from p1 import *

def main():
        data = load_obesity_data()

        num_stats, cat_stats = calculate_and_save_statistics_separate(data)

        hist_plot_hue(data)
        hist_plot(data)
        heat_map(data)
        violin_plot(data)
        reg_plot(data)
        box_plot(data)
        error_bars(data)

if __name__ == "__main__":
    main()

