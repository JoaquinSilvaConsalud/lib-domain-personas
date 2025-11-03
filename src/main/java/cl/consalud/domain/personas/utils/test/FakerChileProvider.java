package cl.consalud.domain.personas.utils.test;

import cl.consalud.domain.common.model.Region;
import net.datafaker.providers.base.AbstractProvider;
import net.datafaker.providers.base.BaseProviders;

public class FakerChileProvider extends AbstractProvider<BaseProviders> {


    public String region() {
        return Region.values()[faker.random().nextInt(Region.values().length)].getNombre();
    }

    public FakerChileProvider(BaseProviders faker) {
        super(faker);
    }
}
