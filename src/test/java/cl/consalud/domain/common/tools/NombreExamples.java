package cl.consalud.domain.common.tools;

import cl.consalud.domain.common.model.Nombre;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class NombreExamples {

    //Alemanes
    public static Nombre johann = new Nombre(
            Nombre.Uso.OFICIAL,
            List.of("Johann", "Friedrich"),
            List.of(new Nombre.Apellido("Müller-Schneider", Nombre.TipoApellido.MATRIMONIO, 0)),
            List.of("Dr.", "Prof.", "Herr"),
            null);

    public static Nombre annaliese = new Nombre(
            Nombre.Uso.OFICIAL,
            List.of("Anneliese"),
            List.of(new Nombre.Apellido("Bauer", Nombre.TipoApellido.FAMILIAR, 0)),
            null,
            null);

    //Arabes
    public static Nombre ali = new Nombre(
            Nombre.Uso.OFICIAL,
            List.of("Ali"),
            List.of(new Nombre.Apellido("bin Yusuf", Nombre.TipoApellido.PATERNO, 0),
                    new Nombre.Apellido("al-Qurashi", Nombre.TipoApellido.TRIBAL, 1)),
            null,
            null
    );

    public static Nombre fatimah = new Nombre(
            Nombre.Uso.OFICIAL,
            List.of("Fatimah", "al-Zahra"),
            List.of(new Nombre.Apellido("bint Khalid", Nombre.TipoApellido.PATERNO, 0),
                    new Nombre.Apellido("al-Mansuri", Nombre.TipoApellido.TRIBAL, 1)),
            null,
            null
    );

    //Chinos
    public static Nombre wei = new Nombre(
            Nombre.Uso.OFICIAL,
            List.of("Wei"),
            List.of(new Nombre.Apellido("Wáng", Nombre.TipoApellido.FAMILIAR, 0)),
            null,
            null
    );


    public static Nombre xiaolong = new Nombre(
            Nombre.Uso.OFICIAL,
            List.of("Xiaolong"),
            List.of(new Nombre.Apellido("Zhang", Nombre.TipoApellido.PATERNO, 0),
                    new Nombre.Apellido("Li", Nombre.TipoApellido.MATERNO, 1)),
            null,
            null
    );

    //Latinos
    public static Nombre mariaFer = new Nombre(
            Nombre.Uso.OFICIAL,
            List.of("María", "Fernanda"),
            List.of(
                    new Nombre.Apellido("González", Nombre.TipoApellido.PATERNO, 0),
                    new Nombre.Apellido("Pérez", Nombre.TipoApellido.MATERNO, 1)
            ),
            null,
            null
    );

    public static Nombre mariaFer2 = new Nombre(
            Nombre.Uso.ALIAS,
            List.of("Mafer"),
            List.of(
                    new Nombre.Apellido("González", Nombre.TipoApellido.PATERNO, 0)
            ),
            null,
            null
    );


    public static Nombre diego = new Nombre(
            Nombre.Uso.OFICIAL,
            List.of("Diego", "Armando"),
            List.of(
                    new Nombre.Apellido("Ruiz", Nombre.TipoApellido.PATERNO, 0),
                    new Nombre.Apellido("Martínez", Nombre.TipoApellido.MATERNO, 1)
            ),
            null,
            null
    );

    //Rusos
    public static Nombre ivan = new Nombre(
            Nombre.Uso.OFICIAL,
            List.of("Ivan", "Sergeevich"),
            List.of(new Nombre.Apellido("Petrov-Sidorov", Nombre.TipoApellido.COMPUESTO, 0)),
            null,
            null
    );

    public static Nombre anastasia = new Nombre(
            Nombre.Uso.OFICIAL,
            List.of("Anastasia", "Dmitrievna"),
            List.of(new Nombre.Apellido("Volkova", Nombre.TipoApellido.FAMILIAR, 0)),
            null,
            null
    );

    public static Nombre anastasia2 = new Nombre(
            Nombre.Uso.ALIAS,
            List.of("Nastya"),
            List.of(),
            null,
            null
    );

    public static Nombre anastasia3 = new Nombre(
            Nombre.Uso.SOCIAL,
            List.of("Nastenka"),
            List.of(),
            null,
            null
    );

    // Japoneses (apellido único)
    public static Nombre taro = new Nombre(
            Nombre.Uso.OFICIAL,
            List.of("Tarō"),
            List.of(new Nombre.Apellido("Yamada", Nombre.TipoApellido.FAMILIAR, 0)),
            null,
            null
    );

    public static Nombre misaki = new Nombre(
            Nombre.Uso.OFICIAL,
            List.of("Misaki"),
            List.of(new Nombre.Apellido("Satō", Nombre.TipoApellido.FAMILIAR, 0)),
            null,
            null
    );

    // Tailandeses (un solo apellido)
    public static Nombre prawit = new Nombre(
            Nombre.Uso.OFICIAL,
            List.of("Prawit"),
            List.of(new Nombre.Apellido("Chaiwat", Nombre.TipoApellido.UNICO, 0)),
            null,
            null
    );

    public static Nombre suchada = new Nombre(
            Nombre.Uso.OFICIAL,
            List.of("Suchada"),
            List.of(new Nombre.Apellido("Phakdee", Nombre.TipoApellido.UNICO, 0)),
            null,
            null
    );

    // Indios
    public static Nombre anil = new Nombre(
            Nombre.Uso.OFICIAL,
            List.of("Anil", "Kumar"),
            List.of(new Nombre.Apellido("Sharma", Nombre.TipoApellido.FAMILIAR, 0)),
            null,
            null
    );

    public static Nombre priyanka = new Nombre(
            Nombre.Uso.OFICIAL,
            List.of("Priyanka"),
            List.of(
                    new Nombre.Apellido("Choudhary", Nombre.TipoApellido.FAMILIAR, 0),
                    new Nombre.Apellido("Singh", Nombre.TipoApellido.TRADICIONAL, 1)
            ),
            null,
            null
    );

    // Griegos
    public static Nombre odysseas = new Nombre(
            Nombre.Uso.OFICIAL,
            List.of("Odysseas"),
            List.of(
                    new Nombre.Apellido("Papadopoulos", Nombre.TipoApellido.FAMILIAR, 0),
                    new Nombre.Apellido("Karagiannis", Nombre.TipoApellido.FAMILIAR, 1)
            ),
            null,
            null
    );

    public static Nombre eleni = new Nombre(
            Nombre.Uso.OFICIAL,
            List.of("Eleni"),
            List.of(
                    new Nombre.Apellido("Papadopoulou", Nombre.TipoApellido.FAMILIAR, 0),
                    new Nombre.Apellido("Nikolaidou", Nombre.TipoApellido.FAMILIAR, 1)
            ),
            null,
            null
    );
    // Hebreos
    public static Nombre shimon = new Nombre(
            Nombre.Uso.OFICIAL,
            List.of("Shimon"),
            List.of(
                    new Nombre.Apellido("Levi", Nombre.TipoApellido.TRIBAL, 0),
                    new Nombre.Apellido("Kohen", Nombre.TipoApellido.TRADICIONAL, 1)
            ),
            null,
            null
    );

    // Vietnamita (apellido → middle name → nombre; aquí en listas de nombres)
    public static Nombre thi = new Nombre(
            Nombre.Uso.OFICIAL,
            List.of("Thị", "Minh", "Hoa"),
            List.of(new Nombre.Apellido("Nguyễn", Nombre.TipoApellido.FAMILIAR, 0)),
            null,
            null
    );

    // Coreano (un solo apellido)
    public static Nombre minJun = new Nombre(
            Nombre.Uso.OFICIAL,
            List.of("Min-jun"),
            List.of(new Nombre.Apellido("Kim", Nombre.TipoApellido.UNICO, 0)),
            null,
            null
    );


    // Húngaro (nativo: apellidos antes del nombre; aquí modelado como dos apellidos)
    public static Nombre arpad = new Nombre(
            Nombre.Uso.OFICIAL,
            List.of("Árpád"),
            List.of(
                    new Nombre.Apellido("Nagy", Nombre.TipoApellido.FAMILIAR, 0),
                    new Nombre.Apellido("Kovács", Nombre.TipoApellido.FAMILIAR, 1)
            ),
            null,
            null
    );

    // Escandinavo
    public static Nombre leif = new Nombre(
            Nombre.Uso.OFICIAL,
            List.of("Leif"),
            List.of(
                    new Nombre.Apellido("Andersson", Nombre.TipoApellido.PATRONIMICO, 0),
                    new Nombre.Apellido("Björk", Nombre.TipoApellido.FAMILIAR, 1)
            ),
            null,
            null
    );

    // Finlandés
    public static Nombre aino = new Nombre(
            Nombre.Uso.OFICIAL,
            List.of("Aino"),
            List.of(
                    new Nombre.Apellido("Kallio", Nombre.TipoApellido.FAMILIAR, 0),
                    new Nombre.Apellido("Virtanen", Nombre.TipoApellido.FAMILIAR, 1)
            ),
            null,
            null
    );

    // Francés (apellido doble con guion)
    public static Nombre jeanpierre = new Nombre(
            Nombre.Uso.OFICIAL,
            List.of("Jean", "Pierre"),
            List.of(new Nombre.Apellido("Dupont-Lefebvre", Nombre.TipoApellido.COMPUESTO, 0)),
            null,
            null
    );

    // Inglés con prefijo/sufijos (doble honorífico académico)
    public static Nombre jonathan = new Nombre(
            Nombre.Uso.OFICIAL,
            List.of("Jonathan", "Edward"),
            List.of(new Nombre.Apellido("Harrington", Nombre.TipoApellido.FAMILIAR, 0)),
            List.of("Sir"),
            List.of("OBE", "PhD")
    );

    // Portugués (dos apellidos; orden frecuente materno → paterno)
    public static Nombre jose = new Nombre(
            Nombre.Uso.OFICIAL,
            List.of("José"),
            List.of(
                    new Nombre.Apellido("da Silva", Nombre.TipoApellido.FAMILIAR, 0),
                    new Nombre.Apellido("Sousa", Nombre.TipoApellido.FAMILIAR, 1)
            ),
            null,
            null
    );

    // Brasileño
    public static Nombre ana = new Nombre(
            Nombre.Uso.OFICIAL,
            List.of("Ana", "Clara"),
            List.of(
                    new Nombre.Apellido("Souza", Nombre.TipoApellido.FAMILIAR, 0),
                    new Nombre.Apellido("Lima", Nombre.TipoApellido.FAMILIAR, 1)
            ),
            null,
            null
    );

    // Árabe (variante femenina adicional)
    public static Nombre laila = new Nombre(
            Nombre.Uso.OFICIAL,
            List.of("Laila"),
            List.of(
                    new Nombre.Apellido("bint Ahmed", Nombre.TipoApellido.PATERNO, 0),
                    new Nombre.Apellido("al-Harbi", Nombre.TipoApellido.TRIBAL, 1)
            ),
            null,
            List.of("PhD")
    );

    // Español adicional
    public static Nombre juan = new Nombre(
            Nombre.Uso.OFICIAL,
            List.of("Juan", "Pablo"),
            List.of(
                    new Nombre.Apellido("García", Nombre.TipoApellido.PATERNO, 0),
                    new Nombre.Apellido("Márquez", Nombre.TipoApellido.MATERNO, 1)
            ),
            null,
            null
    );

    // Italiano (dos apellidos posibles)
    public static Nombre lucia = new Nombre(
            Nombre.Uso.OFICIAL,
            List.of("Lucía"),
            List.of(
                    new Nombre.Apellido("Bianchi", Nombre.TipoApellido.FAMILIAR, 0),
                    new Nombre.Apellido("Rossi", Nombre.TipoApellido.FAMILIAR, 1)
            ),
            List.of("Dr."),
            null
    );

    // Polaco
    public static Nombre piotr = new Nombre(
            Nombre.Uso.OFICIAL,
            List.of("Piotr"),
            List.of(
                    new Nombre.Apellido("Nowak", Nombre.TipoApellido.FAMILIAR, 0),
                    new Nombre.Apellido("Kowalski", Nombre.TipoApellido.FAMILIAR, 1)
            ),
            List.of("Prof."),
            null
    );

    // Turco (generalmente un apellido)
    public static Nombre ayse = new Nombre(
            Nombre.Uso.OFICIAL,
            List.of("Ayse"),
            List.of(new Nombre.Apellido("Demir", Nombre.TipoApellido.UNICO, 0)),
            null,
            null
    );

    // Persa (apellido + nisba como segundo apellido si aplica)
    public static Nombre reza = new Nombre(
            Nombre.Uso.OFICIAL,
            List.of("Reza"),
            List.of(
                    new Nombre.Apellido("Hosseini", Nombre.TipoApellido.FAMILIAR, 0),
                    new Nombre.Apellido("Tabrizi", Nombre.TipoApellido.TRIBAL, 1) // nisba geográfica
            ),
            null,
            null
    );

    // Escocés (apellido compuesto con guion)
    public static Nombre fiona = new Nombre(
            Nombre.Uso.OFICIAL,
            List.of("Fiona"),
            List.of(new Nombre.Apellido("MacLeod-MacKenzie", Nombre.TipoApellido.COMPUESTO, 0)),
            null,
            null
    );

    public static Nombre duncan = new Nombre(
            Nombre.Uso.OFICIAL,
            List.of("Duncan"),
            List.of(new Nombre.Apellido("MacLeod", Nombre.TipoApellido.FAMILIAR, 0)),
            null,
            null
    );

    public static Nombre oluwaseun = new Nombre(
            Nombre.Uso.OFICIAL,
            List.of("Oluwaseun"),
            List.of(
                    new Nombre.Apellido("Adeyemi", Nombre.TipoApellido.FAMILIAR, 0),
                    new Nombre.Apellido("Balogun", Nombre.TipoApellido.FAMILIAR, 1)
            ),
            null,
            null
    );

    public static Nombre picasso = new Nombre(
            Nombre.Uso.OFICIAL,
            List.of("Pablo", "Diego", "José", "Francisco", "de Paula", "Juan Nepomuceno", "María de los Remedios", "Cipriano", "de la Santísima Trinidad"),
            List.of(
                    new Nombre.Apellido("Ruiz", Nombre.TipoApellido.PATERNO, 0),
                    new Nombre.Apellido("Picasso", Nombre.TipoApellido.MATERNO, 1)
            ),
            null,
            null
    );

    public static Nombre picasso2 = new Nombre(
            Nombre.Uso.SOCIAL,
            List.of("Pablo"),
            List.of(
                    new Nombre.Apellido("Picasso", Nombre.TipoApellido.MATERNO, 1)
            ),
            null,
            null
    );

    public static List<Nombre> all = List.of(
        johann, annaliese, ali, fatimah, wei, xiaolong, mariaFer, diego, ivan, anastasia, taro, misaki, prawit, suchada, anil, priyanka,
            odysseas, eleni, shimon, thi, minJun, arpad, leif, aino, jeanpierre, jonathan, jose, ana,
            laila, juan, lucia, piotr, ayse, reza, fiona, duncan, oluwaseun, picasso
    );



    static {

        // Árabes
        ali.setTextoOriginal("علي بن يوسف القرشي");
        fatimah.setTextoOriginal("فاطمة الزهراء بنت خالد المنصوري");
        laila.setTextoOriginal("ليلى بنت أحمد الحربي");

        // Chinos
        wei.setInvertido(true);
        wei.setTextoOriginal("王伟");

        xiaolong.setInvertido(true);
        xiaolong.setTextoOriginal("张李小龙");

        // Japoneses
        taro.setInvertido(true);
        taro.setTextoOriginal("山田 太郎");

        misaki.setInvertido(true);
        misaki.setTextoOriginal("佐藤 美咲");

        // Coreano
        minJun.setInvertido(true);
        minJun.setTextoOriginal("김민준");

        // Vietnamita
        thi.setInvertido(true);
        thi.setTextoOriginal("阮氏明花"); // Nguyễn Thị Minh Hoa

        // Húngaro
        arpad.setInvertido(true);
        arpad.setTextoOriginal("Nagy Kovács Árpád");

        // Hebreo
        shimon.setTextoOriginal("שמעון לוי כהן");

        // Griego
        odysseas.setTextoOriginal("Οδυσσέας Παπαδόπουλος Καραγιάννης");
        eleni.setTextoOriginal("Ελένη Παπαδοπούλου Νικολαΐδου");

        // Ruso
        ivan.setTextoOriginal("Иван Сергеевич Петров-Сидоров");
        anastasia.setTextoOriginal("Анастасия Дмитриевна Волкова");

        // Tailandeses
        prawit.setTextoOriginal("ประวิทย์ ชัยวัฒน์");
        suchada.setTextoOriginal("สุชาดา ภักดี");

        // Indios
        anil.setTextoOriginal("अनिल कुमार शर्मा");
        priyanka.setTextoOriginal("प्रियंका चौधरी सिंह");

        // Turcos
        ayse.setTextoOriginal("Ayşe Demir");

        // Persa
        reza.setTextoOriginal("رضا حسینی تبریزی");
        picasso.setTextoOriginal("Pablo Diego José Francisco de Paula Juan Nepomuceno María de los Remedios Cipriano de la Santísima Trinidad Ruiz y Picasso");
    }

    public static void main(String[] args){

        try {
            Files.createDirectories(Path.of("nombres"));
        } catch (IOException e) {
            throw new RuntimeException("No se pudo crear el directorio destino: " + "nombres", e);
        }

        for (Nombre nombre : List.of(picasso)) {
            try {
                // Normalizamos el nombre del archivo usando el texto (sin espacios ni caracteres raros)
                String base = nombre.getNombres().getFirst()
                                    .replaceAll("[^a-zA-Z0-9]", "_"); // Reemplaza espacios y símbolos
                if (base.isBlank()) {
                    base = nombre.getNombres().getFirst().toUpperCase();
                }

                Path file = Path.of("nombres", base + ".json");

                Files.writeString(file, new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(nombre));
                System.out.println("Archivo escrito: " + file.toAbsolutePath());

            } catch (Exception e) {
                System.err.println("Error escribiendo archivo para: " + nombre + " -> " + e.getMessage());
            }
        }
    }
}
