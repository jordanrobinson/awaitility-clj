# frozen_string_literal: true

require 'rake_leiningen'
require 'rake_terraform'
require 'rake_vault'
require 'vault'
require 'yaml'
require 'rake_fly'
require 'uri'

require_relative 'lib/leiningen_task_set'

task default: %i[library:check library:test:unit]

RakeFly.define_installation_tasks(version: '7.9.0')

RakeLeiningen.define_installation_tasks(
  version: '2.10.0'
)

RakeVault.define_installation_tasks(
  path: File.join(Dir.pwd, 'vendor', 'vault'),
  version: '1.11.2'
)

namespace :vault do
  RakeVault.define_login_task(
    argument_names: [:role, :address],
  ) do |t, args|
    t.address = args[:address]
    t.role = args[:role] || 'read-only'
  end
end

namespace :library do
  define_check_tasks(fix: true)

  namespace :test do
    RakeLeiningen.define_test_task(
      name: :unit,
      type: 'unit',
      profile: 'test')
  end

  namespace :publish do
    RakeLeiningen.define_release_task(
      name: :release,
      profile: 'release')  do |t|
          t.environment = {
              'VERSION' => ENV['VERSION'],
              'CLOJARS_DEPLOY_USERNAME' => ENV['CLOJARS_DEPLOY_USERNAME'],
              'CLOJARS_DEPLOY_TOKEN' => ENV['CLOJARS_DEPLOY_TOKEN']
          }
          end
  end

  desc 'Lint all src files'
  task :lint do
    puts "Running clj-kondo from ./scripts/lint for " + RUBY_PLATFORM
    platform_prefix = /darwin/ =~ RUBY_PLATFORM ? "mac" : "linux"

    sh("./scripts/lint/clj-kondo-2021-06-18-#{platform_prefix}",
       "--lint", "src/")

    puts "Finished running clj-kondo"
  end

  desc 'Reformat all src files'
  task :format do
    puts "Running cljstyle from ./scripts/lint for " + RUBY_PLATFORM
    platform_prefix = /darwin/ =~ RUBY_PLATFORM ? "mac" : "linux"

    sh("./scripts/lint/cljstyle-0-15-0-#{platform_prefix}", "fix")

    puts "Finished running cljstyle"
  end

  task :optimise do
    puts 'skipping optimise...'
  end
  task :idiomise do
    puts 'skipping idiomise...'
  end
end

namespace :ci do
  RakeFly.define_project_tasks(
    pipeline: 'awaitility-clj',
    argument_names: [:concourse_url],
    backend: RakeFly::Tasks::Authentication::Login::FlyBackend
  ) do |t, args|

    t.concourse_url = args[:concourse_url]
    t.config = "pipelines/pipeline.yaml"
    t.non_interactive = true

  end
end
