package day16

class Computer {

	int[] registers = [0, 0, 0, 0]
	OpCodeMapping opCodeMapping

	def addr = { int a, int b, int c ->
		registers[c] = registers[a] + registers[b]
	}

	def addi = { int a, int b, int c ->
		registers[c] = registers[a] + b
	}

	def mulr = { int a, int b, int c ->
		registers[c] = registers[a] * registers[b]
	}

	def muli = { int a, int b, int c ->
		registers[c] = registers[a] * b
	}

	def banr = { int a, int b, int c ->
		registers[c] = registers[a] & registers[b]
	}

	def bani = { int a, int b, int c ->
		registers[c] = registers[a] & b
	}

	def borr = { int a, int b, int c ->
		registers[c] = registers[a] | registers[b]
	}

	def bori = { int a, int b, int c ->
		registers[c] = registers[a] | b
	}

	def setr = { int a, int b, int c ->
		registers[c] = registers[a]
	}

	def seti = { int a, int b, int c ->
		registers[c] = a
	}

	def gtir = { int a, int b, int c ->
		registers[c] = a > registers[b]?1:0
	}

	def gtri = { int a, int b, int c ->
		registers[c] = registers[a] > b ? 1 : 0
	}

	def gtrr = { int a, int b, int c ->
		registers[c] = registers[a] > registers[b] ? 1 : 0
	}

	def eqir = { int a, int b, int c ->
		registers[c] = a == registers[b]?1:0
	}

	def eqri = { int a, int b, int c ->
		registers[c] = registers[a] == b ? 1 : 0
	}

	def eqrr = { int a, int b, int c ->
		registers[c] = registers[a] == registers[b] ? 1 : 0
	}

	def commands = ['addr' : addr, 'addi': addi, 'mulr' : mulr, 'muli' : muli, 'banr': banr, 'bani': bani, 'borr': borr, 'bori': bori, 
		'setr':setr, 'seti':seti, 'gtir':gtir, 'gtri': gtri, 'gtrr': gtrr, 'eqir': eqir, 'eqri': eqri, 'eqrr': eqrr]

	void execute(List<Tuple4> programCommands) {
		programCommands.each { pc ->
			def command = commands[opCodeMapping[pc.first]]
			command.call(pc.second, pc.third, pc.fourth)
		}
	}
}
