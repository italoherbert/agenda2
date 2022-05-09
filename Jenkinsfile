pipeline {
	agent any
	
	stages {
		stage( 'GIT Clone' ) {
			steps {
				sh "if [ -d \"agenda2/\" ]; then rm -r agenda2/; fi"
				sh "git clone https://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@github.com/italoherbert/agenda2.git"
				echo 'Repositório clonado com sucesso.'
			}
		}

		stage( 'Compile e package' ) {
			steps {
				dir( 'agenda2' ) {
					sh "./mvnw clean package -DskipTests"
					echo 'Aplicação compilada e empacotada.'
				}
			}
		}

		stage( 'Build docker image' ) {
			steps {
				dir( 'agenda2' ) {
					echo 'Construindo imagem docker'
				}
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