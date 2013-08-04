package codemarathon

def aliasMaps = ['miles':'mile', 'inches':'inch', 'feet':'foot', 'faths':'fath', 'yards':'yard']
def maps = [:]
def requests = []
def responses = []

FileReader reader = new FileReader('input.txt')

def mapsDone = false
reader.eachLine {
    if (it.trim().isEmpty()) {
        mapsDone = true
    }
    else if (! mapsDone) {
        def map = it.split('=')
        def unit = map[0].split()[1]
        def value = map[1].split()[0].toDouble()
        maps[unit] = value
    } else {
        requests << it
    }
}

requests.each {
    if (it ==~ /.+[\+|\-].+/) {
        def elements = it.split('(?<=[\\+|\\-])|(?=[\\+|\\-])')
        elements = elements.collect {
            if (it != '+' && it != '-') {
                def value = it.split()[0].toDouble()
                def unit = it.split()[1]
                def result = (maps[unit] ?: maps[aliasMaps[unit]]) * value
                return result
            } else {
                return it
            }
        }
        //println elements
        responses << Eval.me(elements.join(' ')).toDouble()
        
    } else {
        def value = it.split()[0].toDouble()
        def unit = it.split()[1]
        responses << (maps[unit] ?: maps[aliasMaps[unit]]) * value
        //println ((maps[unit] ?: maps[aliasMaps[unit]]) + ' * ' + value)
    }
}
//println responses

PrintWriter writer = new PrintWriter('output.txt')
try {
    writer.println 'fifadxj@gmail.com'
    writer.println()
    responses.each {writer.println it.trunc(2) + ' m'}
}
finally {
    writer.close()
}
