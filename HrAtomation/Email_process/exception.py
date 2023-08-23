class GmailException(Exception):
    """gmail base exception class"""


class NoEmailFound(GmailException):
    """no email found"""


class DataNotFoundError(Exception):
    """  no email data found """


class ReadMessageError(Exception):
    """ error during read messages"""
