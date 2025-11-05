trait State {
  def next(): State
  override def toString: String
}

{
  class RedState extends State {
    override def next(): State = RedYellowState.instance

    override def toString: String =
      """-----
        || ● |
        || ◌ |
        || ◌ |
        |-----""".stripMargin
  }

  class RedYellowState extends State {
    override def next(): State = GreenState.instance

    override def toString: String =
      """-----
        || ● |
        || ● |
        || ◌ |
        |-----""".stripMargin
  }

  class GreenState extends State {
    override def next(): State = YellowState.instance

    override def toString: String =
      """-----
        || ◌ |
        || ◌ |
        || ● |
        |-----""".stripMargin
  }

  class YellowState extends State {
    override def next(): State = RedState.instance

    override def toString: String =
      """-----
        || ◌ |
        || ● |
        || ◌ |
        |-----""".stripMargin
  }

  object RedState {
    val instance: RedState = new RedState()
  }

  object RedYellowState {
    val instance: RedYellowState = new RedYellowState()
  }

  object GreenState {
    val instance: GreenState = new GreenState()
  }

  object YellowState {
    val instance: YellowState = new YellowState()
  }
}

class TrafficLights {
  private var currentState: State = RedState.instance

  def next(): Unit =
    currentState = currentState.next()

  override def toString: String = currentState.toString
}


val trafficLights = new TrafficLights
println(trafficLights)
trafficLights.next()
println(trafficLights)
trafficLights.next()
println(trafficLights)
trafficLights.next()
println(trafficLights)
trafficLights.next()
println(trafficLights)
trafficLights.next()
println(trafficLights)
trafficLights.next()
println(trafficLights)
trafficLights.next()
println(trafficLights)
trafficLights.next()
println(trafficLights)
trafficLights.next()
println(trafficLights)
