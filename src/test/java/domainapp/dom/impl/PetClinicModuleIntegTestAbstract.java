package domainapp.dom.impl;

import domainapp.dom.PetClinicModule;
import lombok.Getter;
import org.apache.isis.core.integtestsupport.IntegrationTestAbstract3;
import org.apache.isis.core.metamodel.deployment.DeploymentCategory;
import org.apache.isis.core.metamodel.deployment.DeploymentCategoryProvider;
import org.apache.isis.core.runtime.services.i18n.po.TranslationServicePo;

public abstract class PetClinicModuleIntegTestAbstract extends IntegrationTestAbstract3 {

    public PetClinicModuleIntegTestAbstract() {
        super(new PetClinicModule()
                .withAdditionalServices(DeploymentCategoryProviderForTesting.class)
                .withConfigurationProperty("isis.services.eventbus.implementation","axon")
                .withConfigurationProperty(TranslationServicePo.KEY_PO_MODE, "write")
        );
    }

    public static class DeploymentCategoryProviderForTesting implements DeploymentCategoryProvider {
        @Getter
        DeploymentCategory deploymentCategory = DeploymentCategory.PROTOTYPING;
    }
}