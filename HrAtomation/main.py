import logging

from Resume_process.extraction import Processor
from fastapi.middleware.cors import CORSMiddleware
from fastapi import FastAPI, status, Request
from Email_process.extract_data import EmailData
import uvicorn
from typing import List

app = FastAPI()

origins = [
    "http://localhost:3000"
]
app.add_middleware(
    CORSMiddleware,
    allow_origins=origins,
    allow_credentials=True,
    allow_methods=['*'],
    allow_headers=["*"],
)


@app.post('/Name_Of_Email_Subject', status_code=status.HTTP_201_CREATED)
def create_subject_name(email_subject: List[str]):
    try:
        EmailData().read_message(email_subject)
        return 'Email attachments downloaded'
    except Exception as e:
        logging.error("Error to download resumes from email:%s", str(e))
        return "something went wrong"


@app.get('/cv_data', status_code=status.HTTP_201_CREATED)
def extract_cv_data(request: Request):
    try:
        # Retrieve the schema name from the header
        schema_name = request.headers.get('X-TenantID')

        msg = Processor().cv_process(schema_name)
        if msg:
            return msg
        else:
            return "Resume Not Available"
    except Exception as e:
        logging.exception("Error occurs to add cv data:%s", str(e))
        return "something went wrong"


if __name__ == "__main__":
    uvicorn.run(app, host="192.168.1.100", port=int(7070))