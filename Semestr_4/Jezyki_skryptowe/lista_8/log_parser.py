import re
from datetime import datetime
import pytz

log_pattern = re.compile(
    r'(?P<host_id>\S+) - - \[(?P<datetime_str>.*?)\] '
    r'"(?P<method>[A-Z]+) (?P<resource>\S+) (?P<protocol>.*?)" '
    r'(?P<status_code>\d+) (?P<size>\S+)'
)

class LogEntry:
    def __init__(self, raw_line, host_id, datetime_str, method, resource, protocol, status_code, size):
        self.raw_line = raw_line
        self.host_id = host_id
        self.raw_datetime = datetime_str
        self.timestamp = datetime.strptime(datetime_str, "%d/%b/%Y:%H:%M:%S %z")
        self.method = method
        self.resource = resource
        self.protocol = protocol
        self.status_code = int(status_code)
        self.size = int(size) if size != '-' else 0

    def summary(self):
        return f'{self.host_id} - - [{self.raw_datetime}] "{self.method} {self.resource[:20]}...'

    def details(self):
        return {
            "Remote host": self.host_id,
            "Date": self.timestamp.strftime("%Y-%m-%d"),
            "Time": self.timestamp.strftime("%H:%M:%S"),
            "Timezone": self._find_timezone(),
            "Method": self.method,
            "Resource": self.resource,
            "Protocol": self.protocol,
            "Status code": str(self.status_code),
            "Size": str(self.size) + " Bytes",
        }

    def _find_timezone(self):
        timezone_offset = self.timestamp.utcoffset()
        timezone_str = ""

        for tz in pytz.common_timezones:
            if datetime.now(pytz.timezone(tz)).utcoffset() == timezone_offset:
                timezone_str = tz

        return timezone_str


def parse_log_file(filepath):
    entries = []
    with open(filepath, 'r', encoding='utf-8') as f:
        for line in f:
            match = log_pattern.match(line)
            if match:
                entry = LogEntry(line, **match.groupdict())
                entries.append(entry)
    return entries
