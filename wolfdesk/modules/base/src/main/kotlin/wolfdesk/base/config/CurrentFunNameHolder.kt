package wolfdesk.base.config

internal object CurrentFunNameHolder {
    private val methodNameHolder: ThreadLocal<String> = ThreadLocal()

    var funName: String?
        get() = methodNameHolder.get()
        set(value) = methodNameHolder.set(value)

    fun clear() {
        methodNameHolder.remove()
    }
}
