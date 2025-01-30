#!/bin/bash

# Start code-server (VS Code in web)
code-server --bind-addr 0.0.0.0:80 --auth none --disable-telemetry &

# Start File Browser
filebrowser --port 8081 --no-auth &

# Wait for all background processes
wait
