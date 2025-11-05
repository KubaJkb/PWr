# Obesity Data Analysis

## Overview
This project analyzes obesity-related data using Python. It utilizes `pandas` for data handling, `matplotlib` and `seaborn` for visualization.

## Requirements
Ensure you have Python installed (recommended version 3.8+). 

## Project Structure
├── data/  
│ ├── ObesityDataSet.csv  # Raw data  
│ ├── data_statistics.csv  # Generated statistics  
│ ├── data_statistics_numeric.csv  # Numeric features stats  
│ └── data_statistics_categorical.csv  # Categorical features stats  
├── src/  
│ ├── data_analysis_main.py  # Main data processing script  
│ ├── data_obesity_methods.py  # Data loading/processing methods  
│ └── plots.py  # Visualization functions  
├── requirements.txt  # Python dependencies  
└── README.md  # This file

## Setup
1. Create and activate a virtual environment:
   ```bash
   python -m venv venv
   ```
   ```bash
   source venv/bin/activate  # For Mac/Linux
   ```
   
   ```bash
   venv\Scripts\activate  # For Windows
   ```

2. Install dependencies:
    ```bash
    pip install -r requirements.txt
   ```
   
3. Running the Analysis  

### Basic Usage
   ```
   python src/data_analysis_main.py --plot <plot_type>
   ```

### Boxplot of physical activity by obesity level
   ```bash
   python src/data_analysis_main.py --plot box
   ```

### Violin plot of vegetable consumption
   ```bash
   python src/data_analysis_main.py --plot violin
   ```

### Age vs weight with confidence intervals
   ```bash
   python src/data_analysis_main.py --plot errorbars
   ```

### Distribution of water consumption
   ```bash
   python src/data_analysis_main.py --plot hist
   ```

### Number of people surveyed by age and gender
   ```bash
   python src/data_analysis_main.py --plot hist_hue
   ```

### Correlation matrix heatmap
   ```bash
   python src/data_analysis_main.py --plot heatmap
   ```

### Age-weight regression plot
   ```bash
   python src/data_analysis_main.py --plot regplot
   ```

### Generate all available plots
   ```bash
   python src/data_analysis_main.py --plot all
   ```

### Generate basic statistics report (single file)
   ```bash
   python src/data_analysis_main.py --stats
   ```

### Generate separate statistics for numeric and categorical features
   ```bash
   python src/data_analysis_main.py --stats-separate
   ```

## Data Source

Dataset from UCI Machine Learning Repository:  
https://archive.ics.uci.edu/dataset/544/estimation+of+obesity+levels+based+on+eating+habits+and+physical+condition
