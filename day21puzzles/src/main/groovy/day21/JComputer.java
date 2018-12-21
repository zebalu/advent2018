package day21;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import groovy.lang.Tuple4;

public class JComputer {

	private int ip;
	private int[] registers = { 0, 0, 0, 0, 0, 0 };

	void addr(int a, int b, int c) {
		registers[c] = registers[a] + registers[b];
	}

	void addi(int a, int b, int c) {
		registers[c] = registers[a] + b;
	}

	void mulr(int a, int b, int c) {
		registers[c] = registers[a] * registers[b];
	}

	void muli(int a, int b, int c) {
		registers[c] = registers[a] * b;
	}

	void banr(int a, int b, int c) {
		registers[c] = registers[a] & registers[b];
	}

	void bani(int a, int b, int c) {
		registers[c] = registers[a] & b;
	}

	void borr(int a, int b, int c) {
		registers[c] = registers[a] | registers[b];
	}

	void bori(int a, int b, int c) {
		registers[c] = registers[a] | b;
	}

	void setr(int a, int b, int c) {
		registers[c] = registers[a];
	}

	void seti(int a, int b, int c) {
		registers[c] = a;
	}

	void gtir(int a, int b, int c) {
		registers[c] = a > registers[b] ? 1 : 0;
	}

	void gtri(int a, int b, int c) {
		registers[c] = registers[a] > b ? 1 : 0;
	}

	void gtrr(int a, int b, int c) {
		registers[c] = registers[a] > registers[b] ? 1 : 0;
	}

	void eqir(int a, int b, int c) {
		registers[c] = a == registers[b] ? 1 : 0;
	}

	void eqri(int a, int b, int c) {
		registers[c] = registers[a] == b ? 1 : 0;
	}

	void eqrr(int a, int b, int c) {
		registers[c] = registers[a] == registers[b] ? 1 : 0;
	}

	public int execute(List<Tuple4<String, Integer, Integer, Integer>> programCommands) {
		int last = -1;
		Set<Integer> haltValues = new HashSet<>();
		while (registers[ip] > -1 && registers[ip] < programCommands.size()) {
			Tuple4<String, Integer, Integer, Integer> pc = programCommands.get(registers[ip]);
			statExec(pc.getFirst(), pc.getSecond(), pc.getThird(), pc.getFourth());
			registers[ip]++;
			if (registers[ip] == 28) {
				if (!haltValues.contains(registers[2])) {
					haltValues.add(registers[2]);
					last = registers[2];
				} else {
					registers[ip] = 1000;
				}
				if (haltValues.size() % 1_000 == 0) {
					System.out.println("already found: " + haltValues.size() + " values to halt at");
				}
			}
		}
		System.out.println("last: " + last);
		return last;
	}

	public void setIp(int ip) {
		this.ip = ip;
	}

	public int[] getRegisters() {
		int[] copy = new int[registers.length];
		System.arraycopy(registers, 0, copy, 0, registers.length);
		return copy;
	}

	private void statExec(String command, int a, int b, int c) {
		switch (command) {
		case "addr":
			addr(a, b, c);
			break;
		case "addi":
			addi(a, b, c);
			break;
		case "mulr":
			mulr(a, b, c);
			break;
		case "muli":
			muli(a, b, c);
			break;
		case "banr":
			banr(a, b, c);
			break;
		case "bani":
			bani(a, b, c);
			break;
		case "borr":
			borr(a, b, c);
			break;
		case "bori":
			bori(a, b, c);
			break;
		case "setr":
			setr(a, b, c);
			break;
		case "seti":
			seti(a, b, c);
			break;
		case "gtir":
			gtir(a, b, c);
			break;
		case "gtri":
			gtri(a, b, c);
			break;
		case "gtrr":
			gtrr(a, b, c);
			break;
		case "eqir":
			eqir(a, b, c);
			break;
		case "eqri":
			eqri(a, b, c);
			break;
		case "eqrr":
			eqrr(a, b, c);
			break;
		}
	}
}
