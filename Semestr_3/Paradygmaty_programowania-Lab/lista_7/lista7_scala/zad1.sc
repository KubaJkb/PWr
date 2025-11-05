trait State {
  def next: State
  def toString: String
}

{
  object RedState extends State {
    override def next: State = RedYellowState
    override def toString: String =
      """-----
        || ● |
        || ◌ |
        || ◌ |
        |-----""".stripMargin
  }

  object RedYellowState extends State {
    override def next: State = GreenState
    override def toString: String =
      """-----
        || ● |
        || ● |
        || ◌ |
        |-----""".stripMargin
  }

  object GreenState extends State {
    override def next: State = YellowState
    override def toString: String =
      """-----
        || ◌ |
        || ◌ |
        || ● |
        |-----""".stripMargin
  }

  object YellowState extends State {
    override def next: State = RedState
    override def toString: String =
      """-----
        || ◌ |
        || ● |
        || ◌ |
        |-----""".stripMargin
  }
  
  object FailureState extends State {
    private var previousState: State = RedState
    def setPreviousState(state: State): Unit =
      previousState = state
      
    override def next: State = previousState
    override def toString: String =
      """-----
        || ● |
        || ● |
        || ● |
        |-----""".stripMargin
  }
}

class TrafficLights {
  private var currentState: State = RedState

  def next(): Unit =
    currentState = currentState.next
    
  def enableFailure(): Unit =
    FailureState.setPreviousState(currentState)
    currentState = FailureState
    
  def disableFailure(): Unit =
    currentState = FailureState.next

  override def toString: String = currentState.toString
}


val trafficLights = TrafficLights()

println(trafficLights)
trafficLights.next()
println(trafficLights)
trafficLights.next()
println(trafficLights)
trafficLights.next()
println(trafficLights)

trafficLights.enableFailure()
println(trafficLights)
trafficLights.disableFailure()
println(trafficLights)

trafficLights.next()
println(trafficLights)
trafficLights.next()
println(trafficLights)

//for (_ <- 1 to 10)
//  println(trafficLights)
//  trafficLights.next()
