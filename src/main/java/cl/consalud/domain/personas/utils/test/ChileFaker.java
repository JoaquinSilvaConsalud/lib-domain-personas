package cl.consalud.domain.personas.utils.test;

import net.datafaker.Faker;

public class ChileFaker extends Faker {

    public FakerChileProvider region() {
        return getProvider(FakerChileProvider.class, FakerChileProvider::new);
    }


}
