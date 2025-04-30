import csv

def main():
    filename = 'labeled_newscatcher_dataset.csv'
    
    with open(filename, 'r', newline='', encoding='utf-8') as csvfile:
        csv_reader = csv.reader(csvfile)
        
        # Print each row in the CSV
        i = 0
        for row in csv_reader:
            print(i, row[0].split(";")[1])
            i += 1
            if i > 400:
                break

if __name__ == "__main__":
    main()