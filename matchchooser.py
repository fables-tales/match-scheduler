#!/usr/bin/env python
import sys
import random

def gcd(a, b):
	if b == 0:
		return a
	else:
		return gcd(b, a%b)


def getRandomCoprime(a):
	r = random.randint(2, a-1)

	while (gcd(a, r) != 1):
		r = random.randint(2, a-1)

	return r

if __name__ == "__main__":

	teams = int(sys.argv[1])
	desiredMatches = int(sys.argv[2])
	teamsPerMatch = int(sys.argv[3])
	slots = desiredMatches*teamsPerMatch
	baseMatches = teams*teamsPerMatch/gcd(teams,teamsPerMatch)

	if (desiredMatches % baseMatches != 0):
		print "warning, inexact number of team slots for desired number of matches"
		print "closest number of matches for exact number of slots are"

		if (slots/baseMatches > 0):
			print (slots/baseMatches)*baseMatches/teamsPerMatch, "matches"

		print (slots/baseMatches+1)*baseMatches/teamsPerMatch, "matches"
		print "continuing with unfair number of matches"

	matchcounts = [desiredMatches]*teams
	matches = [None]*desiredMatches

	teamindex = random.randint(0, teams -1)

	stride = getRandomCoprime(teams)

	for i in range(0, desiredMatches):
		match = []

		while len(match) < teamsPerMatch:
			teamindex = (teamindex + stride) % teams

			if teamindex+1 not in match:
				matchcounts[teamindex] -= 1
				match.append(teamindex+1)

		matches[i] = match

	for i in range(0, len(matchcounts)):
		print "Team %d is in %d matches" % (i+1, desiredMatches-matchcounts[i])

	for i in range(0, desiredMatches):
		print "Match number: %d" % (i + 1)
		print "Teams:", matches[i]

	print "writing matches to matches.csv"
	csv = open("matches.csv", "w")

	for match in matches:
		for i in range(0, len(match)):
			csv.write(str(match[i]))

			if i != len(match)-1:
				csv.write(",")
			else:
				csv.write("\n")
			
