sudo docker pull eycha/kboxwoori:0.1.0
sudo docker stop gamza-container || true && sudo docker rm gamza-container || true
docker run -d -p 80:8080 -e TZ=Asia/Seoul -v /home/ubuntu/logs:/logs --name gamza-container eycha/kboxwoori:0.1.0
