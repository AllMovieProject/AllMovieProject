pipeline {
	agent any
	
	environment {
		DOCKER_IMAGE = "necteo/all-movie"
		DOCKER_TAG = "latest"
		CONTAINER = "all-movie"
		EC2_HOST = "100.53.33.30"
		EC2_USER = "ubuntu"
		PORT = "8000"
		COMPOSE_FILE = "~/app/docker-compose.yml"
	}
	
	stages {
		// Git 연결 => Git 주소
		stage('Checkout') {
			steps {
				echo 'Git Checkout'
				checkout scm
			}
		}
		// 배포판 만들기
		stage('Gradle Build') {
			steps {
				echo 'Gradle Build'
				sh '''
						chmod +x gradlew
						./gradlew clean build -x test
					 '''
			}
		}
		
		stage('Docker Build') {
			steps {
				echo 'Docker Image Build'
				sh '''
						docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} .
					 '''
			}
		}
		
		stage('DockerHub Login') {
			steps {
				echo 'DockerHub Login'
				withCredentials([usernamePassword(
					credentialsId: 'dockerhub-credential',
					usernameVariable: 'DOCKER_ID',
					passwordVariable: 'DOCKER_PW'
				)]) {
					sh 'echo $DOCKER_PW | docker login -u $DOCKER_ID --password-stdin'
				}
			}
		}
		
		stage('DockerHub Push') {
			steps {
				echo 'DockerHub Push'
				sh 'docker push ${DOCKER_IMAGE}:${DOCKER_TAG}'
			}
		}
		
		stage('Deploy Docker Compose') {
			steps {
				echo 'Add SSH key'
				sshagent(credentials: ['SERVER_SSH_KEY']) {
					sh """
							ssh-keyscan -t ed25519 ${EC2_HOST} >> ~/.ssh/known_hosts
							ssh -o StrictHostKeyChecking=no ${EC2_USER}@${EC2_HOST} '
								docker compose -f ${COMPOSE_FILE} down || true
								docker pull ${DOCKER_IMAGE}
								docker compose -f ${COMPOSE_FILE} up -d
								'
						 """
				}
			}
		}
	}
	
	post {
		success {
			echo 'CI/CD 실행 성공'
		}
		failure {
			echo 'CI/CD 실행 실패'
		}
	}
}