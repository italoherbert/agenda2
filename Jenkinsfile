pipeline {
	agent any
	
	stages {
		stage( 'Inicializacao' ) {						
			steps {
				script {
					def dockerHome = tool 'JenkinsDocker'	
					env.PATH = "${dockerHome}/bin:${env.PATH}"
				}
			}			
		}
	
		stage( 'GIT Clone' ) {
			environment {
				GITHUB_CREDENCIAIS = credentials( 'github' )
			}
			steps {
				sh "if [ -d \"agenda2/\" ]; then rm -r agenda2/; fi"
				sh "git clone https://${GITHUB_CREDENCIAIS_PSW}@github.com/italoherbert/agenda2.git"
			}
		}

		stage( 'Compile e package' ) {
			steps {
				dir( 'agenda2' ) {
					sh "chmod 777 mvnw"
					sh "./mvnw clean package -DskipTests"
				}
			}
		}

		stage( 'Build docker image' ) {			
			steps {
				dir( 'agenda2' ) {
					sh "docker build -t italoherbert/agenda:latest"
				}
			}
		}
		
		stage( 'Docker hub login' ) {
			environment {
				DOCKER_HUB_CREDENCIAIS = credentials( 'docker-hub' )
			}
			steps {
				sh "echo $DOCKER_HUB_CREDENCIAIS_PSW | docker login -u $DOCKER_HUB_CREDENCIAIS_USR --password-stdin"					
			}
		}
		
		stage( 'Docker image push' ) {
			steps {
				sh "docker push italoherbert/agenda:latest"
			}
		}
		
		stage( 'Implantação no kubernetes' ) {
			steps {
				dir( 'agenda2' ) {
					echo 'Implantando no kubernetes'
				}
			}
		}	
	}
}