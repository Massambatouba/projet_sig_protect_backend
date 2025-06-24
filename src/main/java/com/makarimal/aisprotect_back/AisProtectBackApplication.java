package com.makarimal.aisprotect_back;

import com.makarimal.aisprotect_back.model.*;
import com.makarimal.aisprotect_back.repository.AboutRepository;
import com.makarimal.aisprotect_back.repository.CaracteristiqueRepository;
import com.makarimal.aisprotect_back.repository.CompanyInfoRepository;
import com.makarimal.aisprotect_back.repository.ServiceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Map;

@SpringBootApplication
public class AisProtectBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(AisProtectBackApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(CompanyInfoRepository companyRepo, ServiceRepository serviceRepo, CaracteristiqueRepository caracteristiqueRepository, AboutRepository aboutRepo) {
		return args -> {
			// Vérifie et insère les infos de l'entreprise
			if (companyRepo.count() == 0) {
				CompanyInfo companyInfo = new CompanyInfo();
				companyInfo.setName("SIG Protect");
				companyInfo.setSlogan("Votre sécurité, notre priorité");
				companyInfo.setFoundedYear(2012);
				companyInfo.setDescription("SIG Protect est une entreprise de sécurité professionnelle offrant des solutions complètes pour protéger vos biens et vos proches.");

				companyInfo.setContact(Map.of(
						"phone", "+33 1 23 45 67 89",
						"email", "contact@aisprotect.com",
						"address", "22 Petit Chemin de Brentin 95100 Argenteuil",
						"emergencyPhone", "+33 1 98 76 54 32"
				));

				companyInfo.setSocialMedia(Map.of(
						"facebook", "https://facebook.com/aisprotect",
						"twitter", "https://twitter.com/aisprotect",
						"linkedin", "https://linkedin.com/company/aisprotect",
						"instagram", "https://instagram.com/aisprotect"
				));

				companyRepo.save(companyInfo);
			}

			if (serviceRepo.count() == 0) {
				Service service1 = new Service(null, "Sûreté", "\uD83D\uDC6E",
						"L’agent de sûreté est responsable de la protection des personnes et des biens qui lui sont confiés, garantissant ainsi un environnement sécurisé.\n" +
								"\n" +
								"Ses missions principales incluent le contrôle d’accès, le filtrage des individus, la palpation de sécurité et la surveillance générale du site. Il veille également à prévenir les incidents et à réagir efficacement en cas de situation suspecte ou de menace.\n" +
								"\n" +
								"Afin d’exercer ses fonctions avec professionnalisme, il a suivi plusieurs formations obligatoires, notamment le CQP APS (Certificat de Qualification Professionnelle Agent de Prévention et de Sécurité), le SST (Sauveteur Secouriste du Travail) ainsi que la formation à la palpation de sécurité.\n" +
								"\n" +
								"En complément, SIG Protect assure sa formation aux gestes et postures, essentiels pour prévenir les troubles musculo-squelettiques, ainsi qu’à la gestion des incivilités et des actes terroristes, afin de lui permettre d’adopter les réactions adaptées face à ces situations.",
						List.of("Filtrage et palpation de sécurité",
								"Formation CQP APS et SST",
								"Gestes et postures",
								"Formation incivilité à l'acte terroriste"));
				service1.setImages(List.of(
						new ServiceImage("images/surete1.jpeg", service1),
						new ServiceImage("images/surete2.jpg", service1)
				));

				Service service2 = new Service(null, "Sécurité Incendie", "\uD83D\uDD25",
						"L’agent de sécurité SSIAP est chargé d'assurer la prévention et la sécurité incendie au sein des ERP (Établissements Recevant du Public) et des IGH (Immeubles de Grande Hauteur). Il joue un rôle essentiel dans la protection des vies humaines et des biens en cas d'incendie.\n" +
								"\n" +
								"Ses missions comprennent la prévention des risques d’incendie, la sensibilisation du personnel aux bonnes pratiques de sécurité, ainsi que l'entretien et la vérification régulière des équipements de secours. En cas de danger, il est responsable de l'alerte, de l’accueil des secours, de l’évacuation du public, de l’assistance aux personnes en difficulté et de l’exploitation du PC Sécurité (Poste Central de Sécurité).\n" +
								"\n" +
								"Pour assurer une mission optimale, l’agent de sécurité SSIAP a suivi plusieurs formations indispensables telles que le SSIAP (Service de Sécurité Incendie et d'Assistance à Personnes), le SST (Sauveteur Secouriste du Travail), le H0B0 (habilitation électrique) et le CQP APS (Certificat de Qualification Professionnelle d’Agent de Prévention et de Sécurité).",
						List.of("Prévention des incendies",
								"Entretien des moyens de secours",
								"Évacuation et assistance",
								"Exploitation du PC Sécurité"));
				service2.setImages(List.of(
						new ServiceImage("images/image_ssiap.png", service2),
						new ServiceImage("images/image_ssiap.png", service2)
				));

				Service service3 = new Service(null, "Événementiel", "\uD83D\uDEA7",
						"Pour vos événements, nous mettons à votre disposition une offre sur mesure, parfaitement adaptée à vos besoins spécifiques.\n" +
								"\n" +
								"L’agent de sûreté événementiel et prestige veille à la sécurité du public et à la bonne gestion des lieux lors de manifestations telles que des salons professionnels, des concerts en plein air, des événements corporatifs, ainsi que des compétitions sportives et des festivals culturels. Il s'assure également du respect des règles de sécurité et de la tranquillité des participants.\n" +
								"\n" +
								"Ses principales missions incluent le filtrage du public, la prévention et la dissuasion des actes malveillants, l’intervention en cas d’urgence, et l’assistance aux personnes pour les premiers secours. Il joue ainsi un rôle crucial pour garantir la sécurité et le bon déroulement de l'événement dans toutes les situations",
						List.of("Contrôle d'accès",
								"Sécurité du public",
								"Prévention et dissuasion",
								"Assistance premiers secours"));
				service3.setImages(List.of(
						new ServiceImage("images/evenementielAgent.jpeg", service3),
						new ServiceImage("images/evenementielAgent.jpeg", service3)
				));

				// Sauvegarder tous les services
				serviceRepo.saveAll(List.of(service1, service2, service3));
			}



			if (caracteristiqueRepository.count() == 0) {
				caracteristiqueRepository.saveAll(List.of(
						new Caracteristique(null, "🛡️", "Protection 24/7", "Nos agents de sécurité sont disponibles 24h/24 et 7j/7 pour assurer votre protection."),
						new Caracteristique(null, "👮", "Personnel qualifié", "Nos agents sont formés aux meilleures pratiques et certifiés pour garantir un service de qualité."),
						new Caracteristique(null, "🔒", "Technologie avancée", "Nous utilisons des équipements de pointe pour une surveillance efficace et fiable."),
						new Caracteristique(null, "📋", "Solutions personnalisées", "Nous adaptons nos services à vos besoins spécifiques pour une sécurité optimale.")
				));
				System.out.println("✅ Features initialisées !");
			}

			if (aboutRepo.count() == 0) {
				About about = new About(
						"À propos de SIG Protect",
						"Votre partenaire de confiance en matière de sécurité depuis 2005",
						"Fondée en 2005, SIG Protect est née de la vision de créer une entreprise de sécurité qui place l'excellence du service et la satisfaction du client au cœur de ses préoccupations.\n\n" +
								"Au fil des années, nous avons développé une expertise solide dans tous les aspects de la sécurité, en nous adaptant constamment aux nouvelles technologies et aux défis émergents.\n\n" +
								"Aujourd'hui, SIG Protect est fière d'être reconnue comme un leader dans le domaine de la sécurité, servant des clients dans toute la France avec des solutions sur mesure qui répondent à leurs besoins spécifiques.",
						"Notre mission est de fournir des services de sécurité de la plus haute qualité, en utilisant les meilleures pratiques et technologies disponibles, pour assurer la tranquillité d'esprit de nos clients.",
						"image_mon_equipe.jpg",
						List.of(
								"Intégrité - Nous agissons avec honnêteté et éthique dans toutes nos interactions.",
								"Excellence - Nous nous efforçons d'atteindre l'excellence dans tous les aspects de notre travail.",
								"Respect - Nous traitons chaque personne avec dignité et respect.",
								"Innovation - Nous recherchons constamment de nouvelles façons d'améliorer nos services.",
								"Responsabilité - Nous assumons la responsabilité de nos actions et de nos résultats."
						),
						"images/image_mon_equipe.png"
				);

				aboutRepo.save(about);
				System.out.println("✅ About initialisé !");
			}





		};

	}
}
