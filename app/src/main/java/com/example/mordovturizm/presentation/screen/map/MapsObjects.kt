package com.example.mordovturizm.presentation.screen.map

import com.example.mordovturizm.R
import com.example.mordovturizm.domain.model.PointModel
import com.yandex.mapkit.geometry.Point

val listOfPoints = listOf(
    PointModel(
        Point(54.181452, 45.181558),
        "Кафедральный собор Феодора Ушакова",
        "ул. Советская, 53, Саранск, Респ. Мордовия, 430005",
        listOf(R.drawable.object1_1),
        "На 62 метра возвышается центральный крест, построен собор в стиле ампир крестовокупольного типа. " +
            "По периметру храма расположены четыре звонницы, на которых размещены 12 колоколов, отлитых по старинным",
    ),
    PointModel(
        Point(54.185199, 45.179833),
        "Мордовский музей имени С.Д. Эрьзи",
        "Коммунистическая ул., 61, Саранск, Респ. Мордовия, 430005.",
        listOf(R.drawable.object_2),
        "Государственное бюджетное учреждение культуры “Мордовский республиканский музей изобразительных искусств им. С.Д. Эрьзи” (учредитель – Министерство культуры, национальной политики и архивного дела Республики Мордовия) имеет статус особо ценного объекта национального и культурного достояния народов Республики Мордовии, неоднократно удостаивался государственных наград. За годы своей деятельности музей внес большой вклад в развитие культуры Мордовии, способствовал возрождению национального искусства и в настоящее время является одним из крупных культурных центров Поволжья. Так же это туристически привлекательный объект Республики Мордовии.",
    ),
    PointModel(
        Point(54.180641, 45.192975),
        "Музей мордовской народной культуры",
        "ул. Советская, 19, Саранск, Респ. Мордовия, 430005",
        listOf(R.drawable.object_3),
        "Музей был открыт 6 октября 1999 года. Он располагается в здании, являющемся памятником провинциальной городской архитектуры начала ХХ века. До 1917 года дом принадлежал саранскому купцу и предпринимателю К.Х. Бараблину.\n" +
            "Собрание музея насчитывает почти 4 тысячи экспонатов и включает коллекции мордовской народной одежды, икон, керамики, бытовой утвари, сельскохозяйственных орудий, инструментов. Постоянная экспозиция музея наглядно и ярко демонстрирует жизнь, быт, традиционные обряды мордвы, а также умение, вкус и выдумку народных мастеров, создателей великолепных образцов народного и художественного самодеятельного творчества.\n" +
            "В музее регулярно проходят различные выставки, посвященные творчеству отдельных художников, а также тематические: «Береста», «Солнечная лоза», «Кружевная сказка», «Наследники Эрьзи. Резьба по дереву», «Батик. Пространство красоты» и др.\n",
    ),

    PointModel(
        Point(54.177748, 45.179689),
        "Мордовский республиканский объединенный краеведческий музей им. И.Д. Воронина",
        "Саранская ул., 2, Саранск, Респ. Мордовия, 430000",
        listOf(R.drawable.object_4),
        "Музей был создан 29 ноября 1918 года. Для \"обозрения\" музей был открыт 15 июня 1919 года. Это была коллекционная выставка фарфора, картин, скульптур и других предметов, пере¬данных из имений и городской управы, в ка¬честве музейного оборудования были ис-пользованы витрины бывшего галантерей¬ного магазина \"Славянский базар\". Музей постоянно устраивал выставки историко-краеведческого и санитарно-просветительного характера. В 1922 году фонды насчитывали около 2,5 тыс. единиц хранения.",
    ),
    PointModel(
        Point(54.718822, 43.225090),
        "Мордовский государственный природный заповедник им. П.Г. Смидовича",
        "ул. Лесная, д. 9а, пос. Пушта, Темниковский район Республики Мордовия",
        listOf(R.drawable.object_5),
        "Федеральное государственное бюджетное учреждение \"Объединенная дирекция Мордовского государственного природного заповедника имени П.Г. Смидовича и национального парка \"Смольный\" (краткое наименование - ФГБУ \"Заповедная Мордовия\") - это природоохранное, научно-исследовательское и эколого-просветительское учреждение, имеющее целью сохранение и изучение естественного хода природных процессов и явлений, генетического фонда растительного и животного мира, отдельных видов и сообществ растений и животных, типичных и уникальных экологических систем, имеющих особую экологическую, историческую и эстетическую ценность, и предназначенных для использования в природоохранных, просветительских, научных и культурных целях и для регулируемого туризма.",
    ),
    PointModel(
        Point(54.161519, 45.255273),
        "Иоанно-Богословский Макаровский мужской монастырь",
        "ул. Нагорная, 35, Макаровка, Респ. Мордовия, 430910",
        listOf(R.drawable.object_6),
        "Земли, на которых находится монастырь, с XVII века принадлежали боярам Полянским. Макар Артемьевич Полянский, от имени которого происходит название погоста и села Макаровка, служил в Москве, вступил во владение землями в 1686 году, и с 1700 года переехал в Саранск из Москвы. После этого на его землях и был основан погост. В 1702 году была освящена церковь Михаила Архангела, а в 1704 году — сохранившийся до настоящего времени собор Иоанна Богослова. Примерно в это же время были построены ограда и две башни. В 1720-е годы была построена колокольня, а в 1800-е годы — Знаменская церковь, почти точно повторяющая архитектуру Михайло-Архангельской.",
    ),
    PointModel(
        Point(54.178797, 45.172485),
        "Музей А.И.Полежаева",
        "Саранская ул., 51, Саранск, Респ. Мордовия, 430005",
        listOf(R.drawable.object_7),
        "Музей выполняет функцию научно-исследовательского центра по изучению жизни и творчества поэта; служит хранилищем поэтических и библиографических произведений А.И. Полежаева; является местом пропаганды литературного наследия мордовской культуры.",
    ),

    PointModel(
        Point(54.653132, 43.164536),
        "Рождество-Богородичный Санаксарский мужской монастырь",
        "Республика Мордовия, Темниковский район, посёлок Санаксарь",
        listOf(R.drawable.object_8),
        "Название своё монастырь получил от расположенного под его стенами небольшого озера Сана́ксар (что на местном наречии означает буквально: «лежащее в болотистой ложбине у возвышенности»). Просуществовав около ста лет, Санаксарская обитель от недостатка средств и братии запустела и была приписана к Саровской пустыни, в самую цветущую её пору.\n" +
            "Период возобновления обители связан с именем Феодора (Ушакова), который был настоятелем Санаксары с 1764 по 1774 год. Высочайшим указом от 7 марта 1765 года велено было Санаксару именоваться монастырём.",
    ),
    PointModel(
        Point(54.185720, 45.224722),
        "Церковь Николая Чудотворца",
        "г. Саранск, ул. Волгоградская, д. 90",
        listOf(R.drawable.object_9),
        "Петропавловская) обветшали настолько, что священник Иоанн Сатурнов на приходском совете предложил построить новый каменный храм. Проект создал архитектор Пензенского епархиального управления А.Е. Эренберг, творчески переработавший один из типовых проектов из альбома Священного Синода 1838 года (в его составлении принял участие знаменитый архитектор К.А. Тон). Строительство храма было начато в 1897, закончено в 1906 году. Главный престол был освящен во имя святых апостолов Петра и Павла, придельный – во имя святителя Николая Мирликийского.",
    ),
)
