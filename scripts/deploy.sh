sudo docker pull eycha/kboxwoori:0.1.0
sudo docker stop gamza-container || true && sudo docker rm gamza-container || true
sudo docker run --name gamza-container -d -p 80:8080 eycha/kboxwoori:0.1.0