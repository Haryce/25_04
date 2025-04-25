class StatsModel(var critRate: Int, var evade: Int, var agility: Int, var ampRadius: Int, var power: Int)
interface IStatsView {
    fun displayStats(changedStat: String)
}
class StatsPresenter(private val view: IStatsView, private val model: StatsModel) {
    fun increaseStat(statName: String, amount: Int) {
        val oldValue = when (statName) {
            "critRate" -> model.critRate
            "evade" -> model.evade
            "agility" -> model.agility
            "ampRadius" -> model.ampRadius
            "power" -> model.power
            else -> return
        }
        when (statName) {
            "critRate" -> model.critRate += amount
            "evade" -> model.evade += amount
            "agility" -> model.agility += amount
            "ampRadius" -> model.ampRadius += amount
            "power" -> model.power += amount
        }
        view.displayStats("$statName increased from $oldValue to ${model.critRate}")
    }
    fun decreaseStat(statName: String, amount: Int) {
        val oldValue = when (statName) {
            "critRate" -> model.critRate
            "evade" -> model.evade
            "agility" -> model.agility
            "ampRadius" -> model.ampRadius
            "power" -> model.power
            else -> return
        }
        when (statName) {
            "critRate" -> model.critRate -= amount
            "evade" -> model.evade -= amount
            "agility" -> model.agility -= amount
            "ampRadius" -> model.ampRadius -= amount
            "power" -> model.power -= amount
        }
        view.displayStats("$statName decreased from $oldValue to ${model.critRate}")
    }
}
class HeroModel(var name: String, var age: Int, var desc: String, var stats: StatsModel)
interface IHeroView {
    fun displayHeroUpdate(changedParams: List<String>)
    fun displayHeroDetails(hero: HeroModel)
}
class HeroPresenter(private val view: IHeroView, private val model: HeroModel) {
    fun updateHero(name: String, desc: String) {
        val changedParams = mutableListOf<String>()
        if (model.name != name) {
            changedParams.add("Name changed from ${model.name} to $name")
            model.name = name
        }
        if (model.desc != desc) {
            changedParams.add("Description changed from ${model.desc} to $desc")
            model.desc = desc
        }
        view.displayHeroUpdate(changedParams)
    }
    fun getStatsPresenter(): StatsPresenter {
        return StatsPresenter(object : IStatsView {
            override fun displayStats(changedStat: String) {
                if (changedStat.isNotEmpty()) {
                    println(changedStat)
                }
            }
        }, model.stats)
    }
}
fun main() {
    val statsModel = StatsModel(critRate = 10, evade = 5, agility = 117, ampRadius = 1, power = 15)
    val heroModel = HeroModel(name = "Nightfall", age = 26, desc = "dt", stats = statsModel)
    val heroView = object : IHeroView {
        override fun displayHeroUpdate(changedParams: List<String>) {
            if (changedParams.isNotEmpty()) {
                for (param in changedParams) {
                    println(param)
                }
            }
        }
        override fun displayHeroDetails(hero: HeroModel) {
            println("Hero: ${hero.name}, Age: ${hero.age}, Description: ${hero.desc}")
            println("Stats: Crit Rate: ${hero.stats.critRate}, Evade: ${hero.stats.evade}, Agility: ${hero.stats.agility}, Amp Radius: ${hero.stats.ampRadius}, Power: ${hero.stats.power}")
        }
    }
    val heroPresenter = HeroPresenter(heroView, heroModel)
    println("Current hero:")
    heroView.displayHeroDetails(heroModel)
    println("\nUpdated hero description:")
    heroPresenter.updateHero("Ek", "td")
    val statsPresenter = heroPresenter.getStatsPresenter()
    println("\nBuffed stats:")
    statsPresenter.increaseStat("critRate", 215)
    println("\nDegraded stats:")
    statsPresenter.decreaseStat("agility", 70)
}
