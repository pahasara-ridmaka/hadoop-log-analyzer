import random
import time

ips = [f"192.168.1.{random.randint(1, 100)}" for _ in range(50)] + [
    "10.0.0.99",
    "172.16.0.42",
]
methods = ["GET", "POST", "PUT", "DELETE"]
statuses = [200, 200, 200, 200, 301, 400, 403, 404, 500]
endpoints = [
    "/index.html",
    "/api/v1/auth",
    "/dashboard",
    "/images/logo.png",
    "/admin/config",
]
with open("access_logs.txt", "w") as f:
    for _ in range(100000):
        ip = random.choice(ips)
        method = random.choice(methods)
        endpoint = random.choice(endpoints)
        status = random.choice(statuses)
        size = random.randint(200, 50000)
        timestamp = time.strftime("%d/%b/%Y:%H:%M:%S +0000")
        f.write(
            f'{ip} - - [{timestamp}] "{method} {endpoint} HTTP/1.1" {status} {size}\n'
        )
