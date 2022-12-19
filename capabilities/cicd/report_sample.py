import pandas as pd
import datetime
import json

data = [
    { 
        'class': 'Year 1', 
        'student count': 20, 
        'room': 'Yellow',
        'info': {
            'teachers': { 
                'math': 'Rick Scott', 
                'physics': 'Elon Mask',
            }
        },
        'students': [
            { 'name': 'Tom', 'sex': 'M' },
            { 'name': 'James', 'sex': 'M' },
        ]
    },
    { 
        'class': 'Year 2', 
        'student count': 25, 
        'room': 'Blue',
        'info': {
            'teachers': { 
                 # no math teacher
                 'physics': 'Albert Einstein'
            }
        },
        'students': [
            { 'name': 'Tony', 'sex': 'M' },
            { 'name': 'Jacqueline', 'sex': 'F' },
        ]
    },
]


newdata = pd.json_normalize(
    data, 
    record_path =['students'], 
    meta=['class', 'room', ['info', 'teachers', 'math']],
    errors='ignore'
)

print(newdata)