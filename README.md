# AvitoFilm
Используемый стек: Kotlin, MVVM, xml, Dagger2, Retrofit2, Paging3, Coroutines, Flow, LiveData, Navigation, Coil

Основной проблемой стало отсутствие фильтрации в "Поиске фильмов по названию", пришлось использовать универсальный поиск. 
Из-за этого пришлось искать способы более правильной реализации Paging элементов. 

При доработке фильтраций понял, что многое можно добавить, при наличии дополнительного времени, например больше параметров, EditView заменить на более подходящие компоненты.

# Инструкция к запуску
Вставить в директорию src\main\java\com\example\avitofilm\utils\Constants.kt значение API_KEY,

Должно выглядеть следующим образом: const val API_KEY = "XXXXXXXXXXXXXXXXXXXXXXXX" // ВСТАВЬ КЛЮЧ СЮДА
