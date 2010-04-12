#!/usr/bin/env python
import sys
import random

class Team:
	def __init__(self, number):
		self.number = number
		self.matches = []

	def get_number_of_matches(self):
		return len(self.matches)

	def get_matches(self):
		r = ""
		for i in self.matches:
			r += str(i._number) + " "

		return r

class Match:
	def __init__(self, number):
		self._number = number
		self._teams = []

	def add_team(self, team):
		self._teams.append(team)
		team.matches.append(self)

	def get_number_of_teams(self):
		return len(self._teams)

	def __str__(self):
		return str(self._number)

	def __unicode__(self):
		return self.__str__()

	def teams(self):
		r = ""

		for t in self._teams:
			r += str(t.number) + " "

		return r


def gcd(a, b):
	if b == 0:
		return a
	else:
		return gcd(b, a%b)

def max_matches_per_team(desired_matches, teams, slots_per_match):
	return (desired_matches*slots_per_match) / teams

def setup_matches(desired_matches, number_of_teams, slots_per_match, byematches = False):
	teams = []

	for i in range(1, number_of_teams+1):
		t = Team(i)
		teams.append(t)

	tlist = teams[0:]

	max_matches = max_matches_per_team(desired_matches, number_of_teams, slots_per_match)
	matches = []

	for i in range(0, desired_matches):
		match = Match(i+1)
		random.shuffle(tlist)

		while match.get_number_of_teams() < slots_per_match:
			#get a choice we can actually use
			choice = random.choice(tlist)

			while (choice in match._teams):
				choice = random.choice(tlist)

			tlist.remove(choice)

			#check the team can actually go into the match
			if byematches == False or choice.get_number_of_matches() < max_matches:
				match.add_team(choice)

			#reinitialise teams if we've used them all
			if len(tlist) == 0:
				tlist = teams[0:]

			if byematches == True:
				broken = 0
				for i in teams:
					if i.get_number_of_matches() != max_matches:
						broken = 1
				if not broken:
					matches.append(match)
					return matches,teams

		matches.append(match)

	return matches, teams

if __name__ == "__main__":

	if len(sys.argv) < 3:
		print 'Usage: matchchooser TEAMS MATCHES [--tpm=num] [--allow-byes]'
		print '\tTEAMS\t- The total number of teams competing'
		print '\tMATCHES\t- the total number of matches desired'
		print '\t--tpm\t- The number of teams per match, defaults to 4'
		print '\t--allow-byes\t- If present allow bye matches'
		sys.exit(1)

	teams = int(sys.argv[1])
	teamsmatches = []
	desiredMatches = int(sys.argv[2])

	#Optional command line args
	allowByes = False
	teamsPerMatch = 4
	for arg in sys.argv[3:]:
		if arg[:6] == '--tpm=':
			teamsPerMatch = int(arg[6:])
		elif arg == '--allow-byes':
			allowByes = True

	slots = desiredMatches*teamsPerMatch
	baseMatches = teams*teamsPerMatch/gcd(teams,teamsPerMatch)

	if (slots % baseMatches != 0):
		print "warning, inexact number of team slots for desired number of matches"
		print "closest number of matches for exact number of slots are"

		if (slots/baseMatches > 0):
			print (slots/baseMatches)*baseMatches/teamsPerMatch, "matches"

		print (slots/baseMatches+1)*baseMatches/teamsPerMatch, "matches"
		print "continuing with unfair number of matches"

	matches, team_o = setup_matches(desiredMatches, teams, teamsPerMatch, allowByes)

	for i in team_o:
		print "Team %d in %d matches:" % (i.number, i.get_number_of_matches()), i.get_matches()
#	random.shuffle(matches)
	for m in matches:
		print "Match %d:" % m._number, m.teams()

	print "writing matches to matches.csv"
	csv = open("matches.csv", "w")

	for match in matches:
		for i in range(0, len(match._teams)):
			csv.write(str(match._teams[i].number))

			if i != len(match._teams)-1:
				csv.write(",")
			else:
				csv.write("\n")

