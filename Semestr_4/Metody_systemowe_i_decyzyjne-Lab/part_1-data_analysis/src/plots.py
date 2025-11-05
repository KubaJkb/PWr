import numpy as np
import seaborn as sns
import matplotlib.pyplot as plt
from scipy import stats
import pandas as pd


def box_plot(data):
    plt.figure(figsize=(16, 8))
    sns.boxplot(
        x='NObeyesdad',
        y='FAF',
        hue='Gender',
        data=data,
        order=['Insufficient_Weight', 'Normal_Weight', 'Overweight_Level_I',
               'Overweight_Level_II', 'Obesity_Type_I', 'Obesity_Type_II', 'Obesity_Type_III'],
        palette={'Female': '#ff6b9d', 'Male': '#5b9bd5'},  # Kolory dla płci
        showmeans=True,
        meanprops={'marker': 'D', 'markerfacecolor': 'black', 'markersize': 8},
        linewidth=1.5,
        width=0.7,
        fliersize=5
    )

    plt.title('Aktywność fizyczna wg poziomu otyłości z podziałem na płeć', fontsize=16, pad=20, fontweight='bold')
    plt.xlabel('Poziom otyłości', fontsize=14, labelpad=10)
    plt.ylabel('Stopień aktywności fizycznej', fontsize=14, labelpad=10)
    plt.xticks(rotation=45, fontsize=12, ha='right')
    plt.yticks(fontsize=12)

    legend = plt.legend(
        title='Płeć',
        title_fontsize=13,
        fontsize=12,
        framealpha=0.9,
        shadow=True,
        borderpad=1
    )
    legend.get_frame().set_facecolor('#f5f5f5')

    plt.grid(axis='y', linestyle=':', alpha=0.4, color='gray')
    sns.despine(left=True, bottom=True)
    plt.tight_layout()
    plt.show()


def violin_plot(data):
    plt.figure(figsize=(14, 7))
    sns.violinplot(
        x='NObeyesdad',
        y='FCVC',
        hue='FAVC',
        data=data,
        order=['Insufficient_Weight', 'Normal_Weight', 'Overweight_Level_I',
               'Overweight_Level_II', 'Obesity_Type_I', 'Obesity_Type_II', 'Obesity_Type_III'],
        split=True,
        inner='box',
        palette={'yes': '#8c564b', 'no': '#17becf'},
        density_norm='width'
    )

    plt.title('Spożycie warzyw wg poziomu otyłości z podziałem na wysokokaloryczną dietę', fontsize=16, pad=20,
              fontweight='bold')
    plt.xlabel('Poziom otyłości', fontsize=14, labelpad=10)
    plt.ylabel('Częstotliwość spożycia warzyw', fontsize=14, labelpad=10)
    plt.xticks(rotation=45, fontsize=12, ha='right')
    plt.yticks(fontsize=12)

    legend = plt.legend(
        title='Wysokokaloryczna dieta?',
        title_fontsize=13,
        fontsize=12,
        framealpha=0.9,
        shadow=True,
        borderpad=1,
        bbox_to_anchor=(0.90, 0.6),
    )
    legend.get_frame().set_facecolor('#f5f5f5')

    plt.grid(axis='y', linestyle=':', alpha=0.4, color='gray')
    sns.despine(left=True, bottom=True)
    plt.tight_layout()
    plt.show()


def error_bars(data):
    plt.figure(figsize=(14, 7))

    age_bins = [10, 20, 25, 30, 35, 40, 50, 65]
    age_labels = ['10-20', '21-25', '26-30', '31-35', '36-40', '41-50', '51-65']
    data['Age_Group'] = pd.cut(data['Age'], bins=age_bins, labels=age_labels)

    sns.pointplot(
        x='Age_Group',
        y='Weight',
        hue='family_history_with_overweight',
        data=data,
        palette={'yes': '#e377c2', 'no': '#9467bd'},
        estimator=np.mean,
        errorbar=('ci', 95),
        capsize=0.1,
        markers=['o', 's'],
        linestyles=['-', '--'],
        err_kws={'linewidth': 1.5}
    )

    plt.title('Średnia waga wg wieku', fontsize=14, pad=20)
    plt.xlabel('Przedział wiekowy (lata)', fontsize=12)
    plt.ylabel('Waga (kg)', fontsize=12)
    plt.legend(title='Otyłość w rodzinie?', bbox_to_anchor=(1.02, 1))
    sns.despine()
    plt.tight_layout()
    plt.show()


def hist_plot(data):
    plt.figure(figsize=(12, 6))
    sns.histplot(
        data=data,
        x='CH2O',
        bins=12,
        color='#04a777',
        alpha=0.7,
        edgecolor='#333333',
        linewidth=1,
        stat='percent'
    )

    # Zaznaczenie rekomendowanego spożycia wody
    plt.axvspan(1.5, 2.5, color='#0496ff', alpha=0.2, label='Zalecany zakres')

    plt.title('Ilość wypijanej wody', fontsize=14, pad=15)
    plt.xlabel('Litry wody dziennie', fontsize=12)
    plt.ylabel('Procent osób', fontsize=12)
    plt.legend()
    sns.despine(left=True)
    plt.grid(axis='y', linestyle=':', alpha=0.4)
    plt.show()


def hist_plot_hue(data):
    plt.figure(figsize=(14, 7))

    sns.histplot(
        data=data,
        x='Age',
        hue='Gender',
        bins=35,  # Liczba słupków
        kde=True,  # Dodanie wygładzonej krzywej
        palette={'Female': '#5b9bd5', 'Male': '#e75480'},
        edgecolor='white',
        linewidth=0.5,
        alpha=0.7,
        multiple='dodge',
        shrink=0.8

    )

    plt.title('Liczba badanych osób w danym wieku z podziałem na płeć', fontsize=16, pad=20, fontweight='bold')
    plt.xlabel('Wiek (lata)', fontsize=14)
    plt.ylabel('Liczba osób', fontsize=14)
    plt.xticks(np.arange(data['Age'].min() + 1, data['Age'].max() + 1, 5), fontsize=12)
    plt.yticks(fontsize=12)

    plt.legend(
        title='Płeć',
        labels=['Kobieta', 'Mężczyzna'],
        fontsize=12,
        title_fontsize=13,
        frameon=True,
        shadow=True
    )

    plt.grid(axis='y', linestyle='--', alpha=0.3)
    sns.despine()
    plt.tight_layout()
    plt.show()


def heat_map(data):
    # macierz korelacji
    corr_matrix = data.select_dtypes(include=['number']).corr()

    plt.figure(figsize=(12, 10))
    sns.heatmap(
        corr_matrix,
        annot=True,
        fmt=".2f",
        cmap='coolwarm',
        center=0,
        vmin=-1, vmax=1,
        linewidths=0.5,
        square=True
    )
    plt.title('Macierz korelacji cech numerycznych', fontsize=14, pad=20)
    plt.xticks(rotation=45, ha='right')
    plt.tight_layout()
    plt.show()


def reg_plot(data):
    plt.figure(figsize=(12, 8))
    ax = sns.regplot(
        data=data,
        x='Age',
        y='Weight',
        scatter_kws={
            'alpha': 0.6,
            's': 80,
            'color': '#3498db',
            'edgecolor': 'w',
            'linewidths': 0.5
        },
        line_kws={
            'color': '#e74c3c',
            'linewidth': 2,
            'label': 'Linia regresji'
        },
        ci=95  # 95% przedział ufności
    )

    slope, intercept, r_value, p_value, _ = stats.linregress(data['Age'], data['Weight'])

    # informacje statystyczne
    stats_text = (
        f"Współczynnik korelacji (r) = {r_value:.2f}\n"
        f"Współczynnik determinacji (R²) = {r_value ** 2:.2f}\n"
        f"Równanie: Weight = {slope:.2f}·Age + {intercept:.2f}"
    )

    plt.text(
        0.68, 0.97,
        stats_text,
        transform=ax.transAxes,
        verticalalignment='top',
        fontsize=12,
        bbox=dict(facecolor='white', alpha=0.8, edgecolor='gray', boxstyle='round')
    )

    # Zaznaczenie średnich wartości
    mean_age = data['Age'].mean()
    mean_weight = data['Weight'].mean()
    ax.axvline(mean_age, color='#2c3e50', linestyle='--', alpha=0.7, label=f'Średni wiek ({mean_age:.1f} lat)')
    ax.axhline(mean_weight, color='#2c3e50', linestyle=':', alpha=0.7, label=f'Średnia waga ({mean_weight:.1f} kg)')

    plt.title('Korelacja liniowa: Wiek vs Waga', fontsize=16, pad=20)
    plt.xlabel('Wiek (lata)', fontsize=14)
    plt.ylabel('Waga (kg)', fontsize=14)
    plt.legend(loc='upper center', fontsize=12)
    plt.grid(alpha=0.2)
    sns.despine()
    plt.tight_layout()
    plt.show()



# def plot_alcohol_smoking_obesity(data):
#     plt.figure(figsize=(12, 8))
#
#     fig, axes = plt.subplots(1, 2, figsize=(16, 6))
#
#     # Wykres 1: Alkohol vs Otyłość
#     sns.countplot(data=data, x='CALC', hue='NObeyesdad', ax=axes[0], palette='viridis')
#     axes[0].set_title('Zależność między spożyciem alkoholu a poziomem otyłości')
#     axes[0].set_xlabel('Częstotliwość spożycia alkoholu')
#     axes[0].set_ylabel('Liczba osób')
#     axes[0].legend(title='Poziom otyłości', bbox_to_anchor=(1.05, 1), loc='upper left')
#
#     # Wykres 2: Palenie vs Otyłość
#     sns.countplot(data=data, x='SMOKE', hue='NObeyesdad', ax=axes[1], palette='magma')
#     axes[1].set_title('Zależność między paleniem papierosów a poziomem otyłości')
#     axes[1].set_xlabel('Czy pali papierosy')
#     axes[1].set_ylabel('Liczba osób')
#     axes[1].legend(title='Poziom otyłości', bbox_to_anchor=(1.05, 1), loc='upper left')
#
#     plt.tight_layout()
#     plt.show()
#
#
# def plot_height_obesity(data):
#     plt.figure(figsize=(12, 8))
#
#     sns.set_style("whitegrid")
#     palette = sns.color_palette("husl", len(data['NObeyesdad'].unique()))
#
#     ax = sns.boxplot(
#         x='NObeyesdad',
#         y='Height',
#         data=data,
#         palette=palette,
#         width=0.6,
#         linewidth=1.5
#     )
#
#     plt.title('Zależność między wzrostem a typem otyłości/nadwagi', pad=20, fontsize=15)
#     plt.xlabel('Typ otyłości/nadwagi', labelpad=10)
#     plt.ylabel('Wzrost (m)', labelpad=10)
#     plt.xticks(rotation=45, ha='right')
#
#     mean_height = data['Height'].mean()
#     plt.axhline(mean_height, color='red', linestyle='--', linewidth=1)
#     plt.text(
#         x=len(data['NObeyesdad'].unique()) - 0.5,
#         y=mean_height + 0.02,
#         s=f'Średnia: {mean_height:.2f}m',
#         color='red'
#     )
#
#     plt.tight_layout()
#     plt.show()
